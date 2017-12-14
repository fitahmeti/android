package com.app.swishd.home.search.fragment;


import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgEditSearchBinding;
import com.app.swishd.home.LocationFromMapActivity;
import com.app.swishd.home.search.interfaces.OnAdvancedSearchCallback;
import com.app.swishd.home.search.interfaces.OnEditCallback;
import com.app.swishd.home.search.model.FindJobModel;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.retrofit.RequestCode;
import com.app.swishd.retrofit.ResponseError;

public class EditSearchFragment extends BaseFragment
        implements View.OnClickListener, OnAdvancedSearchCallback, OnResponseCallback {

    private final int REQUEST_PICK_LOCATION = 9897;
    private final int REQUEST_DROP_LOCATION = 9898;

    private FrgEditSearchBinding mBinding;
    private FindJobModel mModel;
    private boolean isEdited = false;
    private OnEditCallback mCallback;

    public static EditSearchFragment getInstance(FindJobModel model, OnEditCallback onEditCallback) {
        EditSearchFragment fragment = new EditSearchFragment();
        fragment.mModel = model;
        fragment.mCallback = onEditCallback;
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frg_edit_search;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgEditSearchBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData();
        setOnClickListener();
    }

    private void setData() {
        mBinding.frgEditSearchLocationPicker.frgSearchPickLocationText.setText(mModel.getSource_address());
        mBinding.frgEditSearchLocationPicker.frgSearchDropLocationText.setText(mModel.getDestination_address());

        mBinding.frgEditSearchLocationPicker.frgSearchDropLocationText.setTextColor(
                getResources().getColor(android.R.color.black)
        );
        mBinding.frgEditSearchLocationPicker.frgSearchPickLocationText.setTextColor(
                getResources().getColor(android.R.color.black)
        );
    }

    private void setOnClickListener() {
        mBinding.frgEditSearchDone.setOnClickListener(this);
        mBinding.frgEditSearchAdvancedSearch.setOnClickListener(this);
        mBinding.frgEditSearchLocationPicker.frgSearchPickLocation.setOnClickListener(this);
        mBinding.frgEditSearchLocationPicker.frgSearchDropLocation.setOnClickListener(this);
        mBinding.frgEditSearchDelete.setOnClickListener(this);
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
            case R.id.frg_edit_search_done:
                if (isEdited) {
                    doEditSearch();
                } else {
                    getActivity().onBackPressed();
                }
                break;
            case R.id.frg_edit_search_advanced_search:
                ((SearchContainerFragment) getParentFragment()).addFragment(
                        AdvancedSearchFragment.getInstance(this, mModel), true);
                break;
            case R.id.frg_edit_search_delete:
                doDeleteSearch();
                break;
        }
    }

    private void doDeleteSearch() {
        showProgressDialog();
        getApiTask().doDeleteSearch(getContext(), mModel.getId(), this);
    }

    private void doEditSearch() {
        getApiTask().doEditSearch(getContext(), mModel, this);
    }

    @Override
    public void onAdvancedSearchCallback(FindJobModel mModel) {
        this.mModel = mModel;
        isEdited = true;
    }

    @Override
    public void onResponseReceived(Object model, int requestCode) {
        dismissProgressDialog();
        if (requestCode == RequestCode.REQUEST_EDIT_SEARCH) {
            ResponseError responseModel = (ResponseError) model;
            if (responseModel != null && responseModel.getError() != null)
                toast(responseModel.getError() + "");
            mCallback.onSearchEditOk(mModel);
            if (getActivity() != null)
                getActivity().onBackPressed();
        } else if (requestCode == RequestCode.REQUEST_DELETE_SEARCH) {
            mCallback.onDeleteSearch();
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onResponseError(String message) {
        dismissProgressDialog();
        showSnackBar(mBinding, message);
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
        isEdited = true;
        String address = data.getStringExtra(LocationFromMapActivity.ADDRESS);
        Location location =
                data.getParcelableExtra(LocationFromMapActivity.LOCATION);

        if (requestCode == REQUEST_PICK_LOCATION) {
            mBinding.frgEditSearchLocationPicker.frgSearchPickLocationText.setText(address);
            mBinding.frgEditSearchLocationPicker.frgSearchPickLocationText.setTextColor(getActivity()
                    .getResources().getColor(android.R.color.black));
            mModel.setSource_latitude(location.getLatitude());
            mModel.setSource_longitude(location.getLongitude());
            mModel.setSource_address(address);
        } else {
            mBinding.frgEditSearchLocationPicker.frgSearchDropLocationText.setText(address);
            mBinding.frgEditSearchLocationPicker.frgSearchDropLocationText.setTextColor(getActivity()
                    .getResources().getColor(android.R.color.black));
            mModel.setDestination_latitude(location.getLatitude());
            mModel.setDestination_longitude(location.getLongitude());
            mModel.setDestination_address(address);
        }
    }

}
