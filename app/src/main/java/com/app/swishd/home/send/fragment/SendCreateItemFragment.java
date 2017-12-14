package com.app.swishd.home.send.fragment;


import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgSendCreateItemBinding;
import com.app.swishd.home.send.adapter.SelectItemSizeAdapter;
import com.app.swishd.home.send.interfaces.OnCustomPriceCallback;
import com.app.swishd.home.send.model.CreateItemModel;
import com.app.swishd.home.send.model.ResponseItemSize;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.widget.EndlessRecyclerOnScrollListener;

import java.util.Locale;

import io.reactivex.observers.DisposableObserver;

import static com.app.swishd.home.send.fragment.SendInsuranceFragment.ITEM_PRICE;

public class SendCreateItemFragment extends BaseFragment
        implements OnResponseCallback, EndlessRecyclerOnScrollListener.onLastItem,
        View.OnClickListener, OnCustomPriceCallback {

    private FrgSendCreateItemBinding mBinding;
    private SelectItemSizeAdapter mAdapter;
    private ResponseItemSize mItems;
    private int iLimit = 15;
    private int iStart = 0;
    private DisposableObserver mCall;
    private float mPrice = 50;
    private CreateItemModel mModel;

    @Override
    public int getLayout() {
        return R.layout.frg_send_create_item;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgSendCreateItemBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = ((SendFragment) getParentFragment()).getModel();
        setItemPrice();
        getItemSize();
        setOnClickListener();
        ((SendFragment) getParentFragment()).setProgress(1);
    }

    private void setOnClickListener() {
        mBinding.frgSendCreateItemPriceMore.setOnClickListener(this);
        mBinding.frgSendCreateItemContinue.setOnClickListener(this);
        mBinding.frgSendCreateItemPrice.setOnClickListener(this);
    }

    private void getItemSize() {
        showProgressDialog();
        mCall = getApiTask().doGetItemSizes(getContext(), iStart, iLimit, this);
    }

    private void setAdapter() {

        if (getContext() == null && mBinding == null)
            return;
        mAdapter = new SelectItemSizeAdapter(getContext(), mItems.getItems());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mBinding.frgSendCreateItemSizeList.setLayoutManager(manager);
        mBinding.frgSendCreateItemSizeList.setAdapter(mAdapter);
        mBinding.frgSendCreateItemSizeList.addOnScrollListener(new EndlessRecyclerOnScrollListener(
                manager, mBinding.frgSendCreateItemSizeList, this
        ));
    }

    @Override
    public void onResponseReceived(Object model, int requestCode) {
        dismissProgressDialog();
        if (model != null) {
            if (mItems == null) {
                mItems = (ResponseItemSize) model;
                setAdapter();
            } else {
                if (mItems != null && mAdapter != null) {
                    mItems.getItems().addAll(((ResponseItemSize) model).getItems());
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onResponseError(String message) {
        dismissProgressDialog();
        showSnackBar(mBinding, message);
    }

    @Override
    public void onLastItem() {
        iStart = mItems.getItems().size();

        if (mItems.getTotalCount() > iStart) {
            getItemSize();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_send_create_item_price_more:
                if (!mBinding.frgSendItemPriceMoreChecked.isChecked())
                    openCustomPriceFragment();
                else
                    setDefaultPrice();
                break;
            case R.id.frg_send_create_item_continue:
                if (isValid() && mAdapter != null) {
                    setData();
                }
                break;
            case R.id.frg_send_create_item_price:
                openCustomPriceFragment();
                break;
        }
    }

    private void setData() {
        mModel.setsJobTitle(getString(mBinding.frgSendCreateItemTitle));
        mModel.setsPriceValue(mPrice);
        mModel.setsSizeId(mAdapter.getSelectedItem().getId());
        mModel.setsSizeType(mAdapter.getSelectedItem().getSSizeTitle());
        ((SendFragment) getParentFragment()).addFragment(
                new PickLocationFragment(), true);
    }

    private boolean isValid() {
        if (getString(mBinding.frgSendCreateItemTitle).isEmpty()) {
            showSnackBar(mBinding, getString(R.string.e_please_enter_item_title));
            mBinding.frgSendCreateItemTitle.requestFocus();
            return false;
        }
        return true;
    }

    private void setDefaultPrice() {
        mPrice = 50;
        setItemPrice();
        mBinding.frgSendItemPriceMoreChecked.setChecked(false);
    }

    private void openCustomPriceFragment() {
        ((SendFragment) getParentFragment()).addFragment(
                SendInsuranceFragment.getInstance(mPrice, this), true,
                true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCall != null)
            mCall.dispose();
    }

    private void setItemPrice() {
        mBinding.frgSendCreateItemPrice.setText(getInsurancePrice());
    }

    private String getInsurancePrice() {
        return String.format(Locale.getDefault(), getString(R.string.euro_price), mPrice);
    }

    public void OnSelectPriceOkey(Intent data) {
        mPrice = data.getFloatExtra(ITEM_PRICE, 50);
        setItemPrice();
        mBinding.frgSendItemPriceMoreChecked.setChecked(true);
    }
}
