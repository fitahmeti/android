package com.app.swishd.home.search.fragment;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgViewAllSearchBinding;
import com.app.swishd.home.search.adapter.ViewAllSearchAdapter;
import com.app.swishd.home.search.interfaces.OnJobListCallback;
import com.app.swishd.home.search.interfaces.OnViewAllSearchCallback;
import com.app.swishd.home.search.model.DataItem;
import com.app.swishd.home.search.model.FindJobModel;
import com.app.swishd.home.search.model.ResponseSearchList;
import com.app.swishd.home.search.model.job.ResponseJobList;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.retrofit.RequestCode;
import com.app.swishd.utility.Const;
import com.app.swishd.widget.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;

public class ViewAllSearchFragment extends BaseFragment
        implements OnResponseCallback, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnJobListCallback {

    private FrgViewAllSearchBinding mBinding;
    private OnViewAllSearchCallback mOnViewAllSearchCallback;
    private ResponseSearchList mSearchList;
    private ViewAllSearchAdapter mAdapterSearchHistory;
    private DisposableObserver<?> mSearchCall;

    private Integer iSearchStart = 0;
    private Integer iLimit = 15;
    private Integer position;
    private String status;
    private FindJobModel mModel;
    private DisposableObserver<?> mJobCall;


    public static ViewAllSearchFragment getInstance(OnViewAllSearchCallback mOnViewAllSearchCallback,
                                                    ResponseSearchList searchList) {
        ViewAllSearchFragment frgViewAllSearchBinding = new ViewAllSearchFragment();
        frgViewAllSearchBinding.mOnViewAllSearchCallback = mOnViewAllSearchCallback;
        frgViewAllSearchBinding.mSearchList = searchList;
        frgViewAllSearchBinding.iSearchStart = searchList.getData().size();
        return frgViewAllSearchBinding;
    }

    @Override
    public int getLayout() {
        return R.layout.frg_view_all_search;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mSearchList == null) {
            toast("Please use static method for this. Use ViewAllSearchFragment.getInstance()");
            getActivity().onBackPressed();
        }
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgViewAllSearchBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHistorySearchAdapter();
        mBinding.frgViewAllSearchRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mOnViewAllSearchCallback != null && mSearchList != null)
            mOnViewAllSearchCallback.onUpdateList(mSearchList);
    }

    private void setHistorySearchAdapter() {
        mAdapterSearchHistory = new ViewAllSearchAdapter(getContext(),
                mSearchList.getData(), this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.frgViewAllSearchList.recyclerHelperList.setLayoutManager(manager);
        mBinding.frgViewAllSearchList.recyclerHelperList.setAdapter(mAdapterSearchHistory);
        mBinding.frgViewAllSearchList.recyclerHelperList.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager,
                mBinding.frgViewAllSearchList.recyclerHelperList, new EndlessRecyclerOnScrollListener.onLastItem() {
            @Override
            public void onLastItem() {
                iSearchStart = mSearchList.getData().size();
                if (iSearchStart < mSearchList.getTotal())
                    getSearchHistory();
            }
        }));
        setNoDataFound();
    }

    private void getSearchHistory() {
        showProgressDialog();
        mSearchCall = getApiTask().doGetSearchList(getContext(),
                iSearchStart, iLimit, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSearchCall != null)
            mSearchCall.dispose();
        if (mJobCall != null)
            mJobCall.dispose();
        mBinding = null;
        mOnViewAllSearchCallback = null;
        mSearchList = null;
        mAdapterSearchHistory = null;
        mJobCall = null;
        iSearchStart = null;
        iLimit = null;
        position = null;
        status = null;
    }

    @Override
    public void onResponseReceived(Object model, int requestCode) {
        dismissProgressDialog();
        if (requestCode == RequestCode.REQUEST_SEARCH_LIST && mAdapterSearchHistory != null) {
            ResponseSearchList tempList = (ResponseSearchList) model;
            mSearchList.getData().addAll(tempList.getData());
            mAdapterSearchHistory.notifyDataSetChanged();
            setNoDataFound();
        } else if (requestCode == RequestCode.REQUEST_CHANGE_SEARCH_STATUS) {
            if (this.status.equals(Const.SEARCH_STATUS.unsaved.toString()))
                mSearchList.getData().remove(position);
            else
                mSearchList.getData().get(position).setFilterStatus(status);
            if (mAdapterSearchHistory != null)
                mAdapterSearchHistory.notifyDataSetChanged();
            setNoDataFound();
        } else if (requestCode == RequestCode.REQUEST_GET_JOBS) {
            ResponseJobList jobDetails = (ResponseJobList) model;
            mModel.setId(jobDetails.getSearch_Id());
            Log.i("MYTAG", jobDetails.getSearch_Id() + "");
            ((SearchContainerFragment) getParentFragment()).addFragment(
                    JobListFragment.getInstance(jobDetails, mModel, this), true
            );
        }
    }

    private void setNoDataFound() {
        if (mSearchList.getData().size() == 0) {
            mBinding.frgViewAllSearchList.recyclerHelperList.setVisibility(View.GONE);
            mBinding.frgViewAllSearchList.recyclerHelperNoData.setVisibility(View.VISIBLE);
        } else {
            mBinding.frgViewAllSearchList.recyclerHelperList.setVisibility(View.VISIBLE);
            mBinding.frgViewAllSearchList.recyclerHelperNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResponseError(String message) {
        dismissProgressDialog();
        showSnackBar(mBinding, message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.row_view_all_search_item_fav:
                position = (Integer) v.getTag();
                changeStatus(mSearchList.getData().get(position).getId(),
                        mAdapterSearchHistory.isItemSaved(position));
                break;
            case R.id.row_view_all_search_main:
                openEditSearchFragment((Integer) v.getTag());
                break;
        }
    }

    private void changeStatus(String id, boolean status) {
        showProgressDialog();
        if (status)
            this.status = Const.SEARCH_STATUS.unsaved.toString();
        else
            this.status = Const.SEARCH_STATUS.saved.toString();
        getApiTask().doChangedSearchStatus(
                getContext(), id, this.status, this
        );
    }

    @Override
    public void onRefresh() {
        iSearchStart = 0;
        mSearchList.getData().clear();
        mAdapterSearchHistory.notifyDataSetChanged();
        mBinding.frgViewAllSearchRefresh.setRefreshing(false);
        getSearchHistory();
    }

    private void openEditSearchFragment(int mSearchId) {
        DataItem data = mSearchList.getData().get(mSearchId);
        mModel = new FindJobModel();
        mModel.setSource_address(data.getSourceAddress());
        mModel.setDestination_address(data.getDestinationAddress());
        mModel.setSource_latitude(data.getSource().getCoordinates().get(1));
        mModel.setSource_longitude(data.getSource().getCoordinates().get(0));
        mModel.setDestination_latitude(data.getDestination().getCoordinates().get(1));
        mModel.setDestination_longitude(data.getDestination().getCoordinates().get(0));
        if (data.getEveryday() != null && data.getEveryday().size() > 0)
            mModel.setEveryday((ArrayList<String>) data.getEveryday());
        mModel.setAnytime(data.getAnytime());
        mModel.setSpecific_date(data.getSpecificDate());
        mModel.setSwishdoffice(data.getSwishdoffice());

        if (data.getFilterStatus() != null &&
                data.getFilterStatus().equals(Const.SEARCH_STATUS.saved.toString()))
            mModel.setFav(true);

        getSuggestedJobs();

    }

    private void getSuggestedJobs() {
        showProgressDialog();
        if (getContext() != null)
            mJobCall = getApiTask().doGetJobs(getContext(), mModel, this);
    }

    @Override
    public void onJobListCallback() {
        iSearchStart = 0;
        mSearchList.getData().clear();
        mAdapterSearchHistory.notifyDataSetChanged();
        getSearchHistory();
    }
}
