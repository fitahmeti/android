package com.app.swishd.home.search.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgSearchLocationPickerBinding;
import com.app.swishd.home.LocationFromMapActivity;
import com.app.swishd.home.search.adapter.ViewAllSearchAdapter;
import com.app.swishd.home.search.interfaces.OnAdvancedSearchCallback;
import com.app.swishd.home.search.interfaces.OnJobListCallback;
import com.app.swishd.home.search.interfaces.OnSearchLocationPickerCallback;
import com.app.swishd.home.search.model.DataItem;
import com.app.swishd.home.search.model.FindJobModel;
import com.app.swishd.home.search.model.ResponseSearchList;
import com.app.swishd.home.search.model.job.ResponseJobList;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.retrofit.RequestCode;
import com.app.swishd.utility.Const;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;

import static com.app.swishd.utility.Utility.LOG;

public class SearchLocationPickerFragment
        extends BaseFragment implements View.OnClickListener, OnResponseCallback,
        SwipeRefreshLayout.OnRefreshListener, OnAdvancedSearchCallback, OnJobListCallback {

    protected final int REQUEST_PICK_LOCATION = 9897;
    protected final int REQUEST_DROP_LOCATION = 9898;
    protected int requestCode;
    protected OnSearchLocationPickerCallback mCallback;
    private FrgSearchLocationPickerBinding mBinding;
    private Location mPickLocation, mDropLocation;
    private ViewAllSearchAdapter mAdapter;
    private ResponseSearchList mSearchList;
    private int position;
    private String status;
    private FindJobModel mModel;
    private DisposableObserver<?> mJobCall;

    public static SearchLocationPickerFragment getInstance(boolean isPickLocationRequest,
                                                           OnSearchLocationPickerCallback callback) {
        SearchLocationPickerFragment fragment = new SearchLocationPickerFragment();
        fragment.requestCode = (isPickLocationRequest) ? fragment.REQUEST_PICK_LOCATION : fragment.REQUEST_DROP_LOCATION;
        fragment.mCallback = callback;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        startActivityForResult(new Intent(getContext(), LocationFromMapActivity.class), requestCode);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.frg_search_location_picker;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgSearchLocationPickerBinding) binding;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMostUsedSearchList();
        setOnClickListener();
    }

    private void setOnClickListener() {
        mBinding.frgSearchLocationPicker.frgSearchPickLocation.setOnClickListener(this);
        mBinding.frgSearchLocationPicker.frgSearchDropLocation.setOnClickListener(this);
        mBinding.frgSearchAdvancedSearch.setOnClickListener(this);
        mBinding.frgSearchSearch.setOnClickListener(this);
//        mBinding.frgSearchLocationPickerRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_search_pick_location:
                startActivityForResult(new Intent(getContext(), LocationFromMapActivity.class), REQUEST_PICK_LOCATION);
                break;
            case R.id.frg_search_drop_location:
                startActivityForResult(new Intent(getContext(), LocationFromMapActivity.class), REQUEST_DROP_LOCATION);
                break;
            case R.id.frg_search_search:
                if (isValid()) {
                    setSearchData();
                }
                break;
            case R.id.frg_search_advanced_search:
                ((SearchContainerFragment) getParentFragment())
                        .addFragment(AdvancedSearchFragment.getInstance(this, mModel), true);
                break;
            case R.id.row_view_all_search_main:
                openEditSearchFragment((Integer) v.getTag());
                break;
            case R.id.row_view_all_search_item_fav:
                position = (Integer) v.getTag();
                changeStatus(mSearchList.getData().get(position).getId(),
                        mAdapter.isItemSaved(position));
                break;
        }
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

    private void setSearchData() {
        if (mModel == null)
            mModel = new FindJobModel();

        //Pick Location
        mModel.setSource_latitude(mPickLocation.getLatitude());
        mModel.setSource_longitude(mPickLocation.getLongitude());
        mModel.setSource_address(mBinding.frgSearchLocationPicker.frgSearchPickLocationText.getText().toString());

        //Drop Location
        mModel.setDestination_latitude(mDropLocation.getLatitude());
        mModel.setDestination_longitude(mDropLocation.getLongitude());
        mModel.setDestination_address(mBinding.frgSearchLocationPicker.frgSearchDropLocationText.getText().toString());

        getSuggestedJobs();
    }

    private void getSuggestedJobs() {
        showProgressDialog();
        if (getContext() != null)
            mJobCall = getApiTask().doGetJobs(getContext(), mModel, this);
    }

    private boolean isValid() {
        if (mPickLocation == null) {
            showSnackBar(mBinding, getString(R.string.e_please_select_pickup_point));
            return false;
        } else if (mDropLocation == null) {
            showSnackBar(mBinding, getString(R.string.e_please_select_drop_point));
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DROP_LOCATION || requestCode == REQUEST_PICK_LOCATION && resultCode == Activity.RESULT_OK) {
            parseIntent(data, requestCode);
        }
    }

    private void parseIntent(Intent data, int requestCode) {
        if (data == null)
            return;

        String address = data.getStringExtra(LocationFromMapActivity.ADDRESS);
        Location location =
                data.getParcelableExtra(LocationFromMapActivity.LOCATION);

        if (requestCode == REQUEST_PICK_LOCATION) {
            mBinding.frgSearchLocationPicker.frgSearchPickLocationText.setText(address);
            mBinding.frgSearchLocationPicker.frgSearchPickLocationText.setTextColor(getActivity()
                    .getResources().getColor(android.R.color.black));
            mPickLocation = location;
        } else {
            mBinding.frgSearchLocationPicker.frgSearchDropLocationText.setText(address);
            mBinding.frgSearchLocationPicker.frgSearchDropLocationText.setTextColor(getActivity()
                    .getResources().getColor(android.R.color.black));
            mDropLocation = location;
        }
    }

    private void getMostUsedSearchList() {
        showProgressDialog();
        getApiTask().doGetMostUsedSearchList(
                getContext(),
                this
        );
    }

    @Override
    public void onResponseReceived(Object model, int requestCode) {
        dismissProgressDialog();
        if (requestCode == RequestCode.REQUEST_GET_MOST_USED_SEARCH) {
            if (mAdapter != null) {
                mSearchList.getData().clear();
                mSearchList.getData().addAll(((ResponseSearchList) model).getData());
                mAdapter.notifyDataSetChanged();
            } else {
                mSearchList = (ResponseSearchList) model;
                setMostUsedSearchAdapter();
            }
            setSearchNoDataView();
        } else if (requestCode == RequestCode.REQUEST_CHANGE_SEARCH_STATUS) {
            mSearchList.getData().get(position).setFilterStatus(status);
            if (mAdapter != null)
                mAdapter.notifyDataSetChanged();
        } else if (requestCode == RequestCode.REQUEST_GET_JOBS) {
            ResponseJobList jobDetails = (ResponseJobList) model;
            mModel.setId(jobDetails.getSearch_Id());

            if (jobDetails.getFilter_status() != null &&
                    jobDetails.getFilter_status().equals(Const.SEARCH_STATUS.saved.toString()))
                mModel.setFav(true);

            ((SearchContainerFragment) getParentFragment()).addFragment(
                    JobListFragment.getInstance(jobDetails, mModel, this), true
            );
        }
    }

    @Override
    public void onResponseError(String message) {
        dismissProgressDialog();
        showSnackBar(mBinding, message);
    }

    private void setMostUsedSearchAdapter() {
        mAdapter = new ViewAllSearchAdapter(getContext(),
                mSearchList.getData(), this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mBinding.frgSearchListView.recyclerHelperList.setLayoutManager(manager);
        mBinding.frgSearchListView.recyclerHelperList.setAdapter(mAdapter);
    }

    private void setSearchNoDataView() {
        if (mSearchList != null && mSearchList.getData().size() > 0) {
            mBinding.frgSearchListView.recyclerHelperList.setVisibility(View.VISIBLE);
            mBinding.frgSearchListView.recyclerHelperNoData.setVisibility(View.GONE);
        } else {
            mBinding.frgSearchListView.recyclerHelperList.setVisibility(View.GONE);
            mBinding.frgSearchListView.recyclerHelperNoData.setVisibility(View.VISIBLE);
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
        showProgressDialog();
        getMostUsedSearchList();
    }

    @Override
    public void onAdvancedSearchCallback(FindJobModel mModel) {
        this.mModel = mModel;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mJobCall != null) {
            mJobCall.dispose();
        }
        mJobCall = null;
        mBinding = null;
        mAdapter = null;
        mModel = null;
        mPickLocation = null;
        mDropLocation = null;
        mCallback = null;
    }

    @Override
    public void onJobListCallback() {
        onRefresh();
    }

    @Override
    public void onDestroyView() {
        if (mCallback != null)
            mCallback.onSearchLocationCallback();
        super.onDestroyView();
    }
}
