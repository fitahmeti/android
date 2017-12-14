package com.app.swishd.home.search.fragment;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgJobListBinding;
import com.app.swishd.home.search.adapter.JobSuggestedAdapter;
import com.app.swishd.home.search.interfaces.OnEditCallback;
import com.app.swishd.home.search.interfaces.OnJobListCallback;
import com.app.swishd.home.search.model.FindJobModel;
import com.app.swishd.home.search.model.job.ResponseJobList;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.retrofit.RequestCode;
import com.app.swishd.utility.Const;
import com.app.swishd.widget.EndlessRecyclerOnScrollListenerWithLastItemCount;

import io.reactivex.disposables.Disposable;

public class JobListFragment extends BaseFragment
        implements View.OnClickListener, OnResponseCallback, OnEditCallback {

    private ResponseJobList mJobList;
    private JobSuggestedAdapter mJobSuggestedAdapter;
    private Disposable mJobCall;

    private FrgJobListBinding mBinding;
    private OnJobListCallback mCallback;
    private FindJobModel mJobModel;

    private String status;

    public static JobListFragment getInstance(ResponseJobList jobDetails, FindJobModel findModel,
                                              OnJobListCallback callback) {
        JobListFragment jobSearchFragment = new JobListFragment();
        jobSearchFragment.mJobList = jobDetails;
        jobSearchFragment.mJobModel = findModel;
        jobSearchFragment.mCallback = callback;
        return jobSearchFragment;
    }

    private void setData() {
        mBinding.frgJobListLocationPicker.frgSearchPickLocationText.setText(mJobModel.getSource_address());
        mBinding.frgJobListLocationPicker.frgSearchPickLocationText.setTextColor(getActivity()
                .getResources().getColor(android.R.color.black));

        mBinding.frgJobListLocationPicker.frgSearchDropLocationText.setText(mJobModel.getDestination_address());
        mBinding.frgJobListLocationPicker.frgSearchDropLocationText.setTextColor(getActivity()
                .getResources().getColor(android.R.color.black));

        if (mJobModel.isFav())
            mBinding.frgJobItemFav.setActivated(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData();
        setJobAdapter();
        setJobNoDataView();
        setOnClickListener();
    }

    private void setOnClickListener() {
        mBinding.frgJobListEdit.setOnClickListener(this);
        mBinding.frgJobItemFav.setOnClickListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.frg_job_list;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgJobListBinding) binding;
    }

    private void setJobAdapter() {
        if (getContext() == null)
            return;
        mJobSuggestedAdapter = new JobSuggestedAdapter(getContext(), mJobList.getData(), this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mBinding.frgJobListList.recyclerHelperList.setLayoutManager(manager);
        mBinding.frgJobListList.recyclerHelperList.setAdapter(mJobSuggestedAdapter);
        mBinding.frgJobListList.recyclerHelperList.addOnScrollListener(
                new EndlessRecyclerOnScrollListenerWithLastItemCount(
                        manager,
                        mBinding.frgJobListList.recyclerHelperList, new EndlessRecyclerOnScrollListenerWithLastItemCount.onLastItem() {
                    @Override
                    public void onLastItem() {
                        mJobModel.setStart(mJobList.getData().size());
                        if (mJobList.getData().size() < mJobList.getTotal()) {
                            getSuggestedJobs();
                        }
                    }
                }));
    }


    private void setJobNoDataView() {
        if (mJobList != null && mJobList.getData().size() > 0) {
            mBinding.frgJobListList.recyclerHelperList.setVisibility(View.VISIBLE);
            mBinding.frgJobListList.recyclerHelperNoData.setVisibility(View.INVISIBLE);
        } else {
            mBinding.frgJobListList.recyclerHelperList.setVisibility(View.INVISIBLE);
            mBinding.frgJobListList.recyclerHelperNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_job_list_edit:
                ((SearchContainerFragment) getParentFragment()).addFragment(
                        EditSearchFragment.getInstance(mJobModel, this), true
                );
                break;
            case R.id.frg_job__item_fav:
                changeStatus();
                break;
            case R.id.row_job_list_main:
                ((SearchContainerFragment) getParentFragment()).addFragment(
                        SwissJobDetailsFragment.getInstance(mJobList.getData().get((Integer) v.getTag()).getId())
                        , true
                );
                break;

        }
    }

    private void changeStatus() {
        showProgressDialog();
        if (mBinding.frgJobItemFav.isActivated()) {
            status = Const.SEARCH_STATUS.unsaved.toString();
            mBinding.frgJobItemFav.setActivated(false);
        } else {
            status = Const.SEARCH_STATUS.saved.toString();
            mBinding.frgJobItemFav.setActivated(true);
        }
        getApiTask().doChangedSearchStatus(
                getContext(), mJobModel.getId(), this.status, this
        );
    }

    private void getSuggestedJobs() {
        showProgressDialog();
        if (getContext() != null)
            mJobCall = getApiTask().doGetJobs(getContext(), mJobModel, this);
    }

    @Override
    public void onResponseReceived(Object model, int requestCode) {
        dismissProgressDialog();
        if (requestCode == RequestCode.REQUEST_GET_JOBS) {
            if (mJobSuggestedAdapter == null) {
                mJobList = (ResponseJobList) model;
                setJobAdapter();
            } else {
                mJobList.getData().addAll(((ResponseJobList) model).getData());
                mJobSuggestedAdapter.notifyDataSetChanged();
            }
            setJobNoDataView();
        }
    }

    @Override
    public void onResponseError(String message) {
        dismissProgressDialog();
        showSnackBar(mBinding, message);
    }

    @Override
    public void onDestroyView() {
        if (mCallback != null)
            mCallback.onJobListCallback();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mJobCall != null)
            mJobCall.dispose();

        mJobCall = null;
        mCallback = null;
    }

    @Override
    public void onSearchEditOk(FindJobModel model) {
        mJobModel = model;
        mJobModel.setStart(0);
        setData();
        if (mJobList != null && mJobSuggestedAdapter != null) {
            mJobList.getData().clear();
            mJobSuggestedAdapter.notifyDataSetChanged();
        }
        getSuggestedJobs();
    }

    @Override
    public void onDeleteSearch() {
        getHomeActivity().onBackPressed();
    }
}
