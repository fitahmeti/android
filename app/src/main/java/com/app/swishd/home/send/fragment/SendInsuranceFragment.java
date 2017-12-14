package com.app.swishd.home.send.fragment;


import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgSendInsuranceBinding;
import com.app.swishd.home.send.interfaces.OnCustomPriceCallback;

import java.util.Locale;

public class SendInsuranceFragment extends BaseFragment implements View.OnClickListener {

    public static final String ITEM_PRICE = "item_price";

    private FrgSendInsuranceBinding mBinding;
    private float mPrice;
    private OnCustomPriceCallback mCallbackFragment;

    public static SendInsuranceFragment getInstance(float price, OnCustomPriceCallback targetFragment) {
        SendInsuranceFragment fragment = new SendInsuranceFragment();
        Bundle bundle = new Bundle();
        bundle.putFloat(ITEM_PRICE, price);
        fragment.setArguments(bundle);
        fragment.mCallbackFragment = targetFragment;
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frg_send_insurance;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgSendInsuranceBinding) binding;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null || !getArguments().containsKey(ITEM_PRICE))
            return;
        else {
            checkBundle();
            super.onCreate(savedInstanceState);
        }
    }

    private void checkBundle() {
        mPrice = getArguments().getFloat(ITEM_PRICE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnClickListener();
        setInsurancePrice(3f);
        setDefaultSelectedPrice();
    }

    private void setDefaultSelectedPrice() {
        mBinding.frgSendInsuranceItemPrice.setText(
                String.format(Locale.getDefault(), "%.0f", mPrice));
    }

    private void setInsurancePrice(float price) {
        mBinding.frgSendInsurancePrice.setText(getInsurancePrice(price));
    }

    private String getInsurancePrice(float price) {
        return String.format(Locale.getDefault(), getString(R.string.euro_price), price);
    }

    private void setOnClickListener() {
        mBinding.frgSendInsuranceBackArrow.setOnClickListener(this);
        mBinding.frgSendInsuranceAccept.setOnClickListener(this);
        mBinding.frgSendInsuranceDecline.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_send_insurance_back_arrow:
                getActivity().onBackPressed();
                break;
            case R.id.frg_Send_insurance_accept:
                if (isValid())
                    setResultAndFinish();
                break;
            case R.id.frg_Send_insurance_decline:
                getActivity().onBackPressed();
                break;
        }
    }

    private void setResultAndFinish() {
        Intent ivResult = new Intent();
        ivResult.putExtra(ITEM_PRICE, mPrice);
        mCallbackFragment.OnSelectPriceOkey(ivResult);
        getActivity().onBackPressed();
    }

    private boolean isValid() {
        if (getString(mBinding.frgSendInsuranceItemPrice).isEmpty())
            mPrice = 0;
        else
            mPrice = Float.valueOf(getString(mBinding.frgSendInsuranceItemPrice));
        
        if (mPrice < 51) {
            showSnackBar(mBinding, getString(R.string.e_price_must_be_grater_than_50));
            mBinding.frgSendInsuranceItemPrice.requestFocus();
            return false;
        } else if (!mBinding.frgSendInsuranceTermsChecked.isChecked()) {
            showSnackBar(mBinding, getString(R.string.e_please_read_terms_and_conditions));
            return false;
        }
        return true;
    }

}
