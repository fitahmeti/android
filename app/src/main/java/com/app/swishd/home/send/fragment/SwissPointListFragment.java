package com.app.swishd.home.send.fragment;


import android.databinding.ViewDataBinding;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgSwissPointBinding;
import com.app.swishd.home.send.adapter.SwisHdListAdapter;
import com.app.swishd.home.send.interfaces.OnOfficeSelectCallback;
import com.app.swishd.home.send.interfaces.OnSortByDialogCallback;
import com.app.swishd.home.send.model.ResponseOfficeList;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.widget.EndlessRecyclerOnScrollListener;
import com.vinay.utillib.UtilLib;
import com.vinay.utillib.locationmanage.OnLocationPickListener;

import io.reactivex.disposables.Disposable;

public class SwissPointListFragment extends BaseFragment implements OnSortByDialogCallback,
        EndlessRecyclerOnScrollListener.onLastItem, OnResponseCallback<ResponseOfficeList>, OnOfficeSelectCallback {

    private FrgSwissPointBinding mBinding;
    private SwisHdListAdapter mAdapter;
    private ResponseOfficeList mList;
    private int iStart = 0, iLimit = 15;
    private String mSortBy = "";
    private String mLat, mLng;
    private boolean isMore = true;
    private OnOfficeSelectCallback mCallback;
    private Disposable mCall;

    public static SwissPointListFragment getInstance(OnOfficeSelectCallback callback) {
        SwissPointListFragment mFragment = new SwissPointListFragment();
        mFragment.mCallback = callback;
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mCallback == null)
            return;
    }

    @Override
    public int getLayout() {
        return R.layout.frg_swiss_point;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgSwissPointBinding) binding;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SendFragment) getParentFragment()).isVisibleSortByMenu(View.VISIBLE);
        ((SendFragment) getParentFragment()).isVisibleProgressMenu(View.INVISIBLE);
        ((SendFragment) getParentFragment()).setSortByDialogClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLocation();
    }

    private void setAdapter() {
        mAdapter = new SwisHdListAdapter(getContext(), mList, this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mBinding.frgSwissPointList.setLayoutManager(manager);
        mBinding.frgSwissPointList.setAdapter(mAdapter);
        mBinding.frgSwissPointList.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager,
                mBinding.frgSwissPointList, this));
    }

    @Override
    public void onSortSelected(String sortType) {
        mSortBy = sortType;
        iStart = 0;
        mList.getDetail().clear();
        mAdapter.notifyDataSetChanged();
        getOfficeList();
        isMore = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((SendFragment) getParentFragment()).isVisibleSortByMenu(View.INVISIBLE);
        ((SendFragment) getParentFragment()).isVisibleProgressMenu(View.VISIBLE);
    }

    @Override
    public void onLastItem() {
        iStart = mList.getDetail().size();
        if (isMore && mList != null && mList.getDetail().size() > 14)
            getOfficeList();
    }

    private void getOfficeList() {
        showProgressDialog();
        mCall = getApiTask().doGetOfficeList(getContext(),
                mLat,
                mLng,
                mSortBy,
                iStart,
                iLimit,
                this
        );
    }

    @Override
    public void onResponseReceived(ResponseOfficeList model, int requestCode) {
        dismissProgressDialog();
        if (mAdapter == null) {
            mList = model;
            setAdapter();
        } else {
            mList.getDetail().addAll(model.getDetail());
            mAdapter.notifyDataSetChanged();
            if (model.getDetail().size() == 0)
                isMore = false;
        }
    }

    @Override
    public void onResponseError(String message) {
        dismissProgressDialog();
        showSnackBar(mBinding, message);
    }

    public void getLocation() {
        UtilLib.getLocationManager(getContext()).getLocation(new OnLocationPickListener() {
            @Override
            public void getLastLocation(Location location) {
                mLat = location.getLatitude() + "";
                mLng = location.getLongitude() + "";
                getOfficeList();
            }

            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onError(String s) {
                getOfficeList();
            }
        });
    }

    @Override
    public void onOfficeSelected(ResponseOfficeList.DetailItem mItem) {
        mCallback.onOfficeSelected(mItem);
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCall != null)
            mCall.dispose();
    }
}
