package com.app.swishd.home.send.fragment;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgRecommendedPriceBinding;
import com.app.swishd.home.send.model.CreateItemModel;
import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Subscription;
import rx.functions.Action1;

import static com.app.swishd.utility.DateUtil.SPACE;

public class RecommendedPriceFragment extends BaseFragment implements View.OnClickListener {

    private FrgRecommendedPriceBinding mBinding;
    private CreateItemModel mModel;
    private Subscription mSubscriptionCustomPrice;

    @Override
    public int getLayout() {
        return R.layout.frg_recommended_price;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgRecommendedPriceBinding) binding;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SendFragment) getParentFragment()).setProgress(5);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = ((SendFragment) getParentFragment()).getModel();

        mSubscriptionCustomPrice = RxTextView.textChanges(mBinding.frgRecommendedInsurancePriceCustom)
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        if (charSequence.toString().isEmpty()) {
                            mModel.setsRecommendedPriceAndSetRewardPrice(0);
                        } else {
                            mModel.setsRecommendedPriceAndSetRewardPrice(Double.parseDouble(charSequence.toString()));
                        }
                        setValue();
                    }
                });
        mModel.setsRecommendedPriceAndSetRewardPrice(8);
        setTotalPrice();
        setValue();
        setOnClickListener();
    }

    private void setValue() {
        setRewards();
        setInsuranceAndFee();
        setVat();

    }

    private void setOnClickListener() {
        mBinding.frgRecommendedPriceBreakdownDropDown.setOnClickListener(this);
        mBinding.frgRecommendedInsuranceSetOwnPrice.setOnClickListener(this);
        mBinding.frgRecommendedInsuranceSetContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_recommended_price_breakdown_drop_down:
                if (mBinding.frgRecommendedPriceBreakDownMain.getVisibility() == View.VISIBLE) {
                    mBinding.frgRecommendedPriceBreakDownMain.setVisibility(View.GONE);
                    mBinding.frgRecommendedPriceBreakdownDropDownImage.setImageResource(R.drawable.ic_arrow_drop_down);
                } else {
                    mBinding.frgRecommendedPriceBreakDownMain.setVisibility(View.VISIBLE);
                    mBinding.frgRecommendedPriceBreakdownDropDownImage.setImageResource(R.drawable.ic_arrow_drop_up);
                }
                break;
            case R.id.frg_recommended_insurance_set_own_price:
                if (mBinding.frgRecommendedPrice.getVisibility() == View.GONE) {
                    mBinding.frgRecommendedPriceCustomMain.setVisibility(View.GONE);
                    mBinding.frgRecommendedPrice.setVisibility(View.VISIBLE);
                    mBinding.frgRecommendedInsuranceSetOwnPrice.setText(getString(R.string.set_own_price));
                    mBinding.frgRecommendedPriceHeading.setText(getString(R.string.recommended_price));
                    mModel.setsRecommendedPriceAndSetRewardPrice(8);
                    mBinding.frgRecommendedInsurancePriceCustom.setText(mModel.getsRecommendedPrice());
                    setValue();
                    setTotalPrice();
                } else {
                    mBinding.frgRecommendedPriceCustomMain.setVisibility(View.VISIBLE);
                    mBinding.frgRecommendedPrice.setVisibility(View.GONE);
                    mBinding.frgRecommendedInsuranceSetOwnPrice.setText(getString(R.string.revert_to_default_price));
                    mBinding.frgRecommendedPriceHeading.setText(getString(R.string.your_own_price));
                    mBinding.frgRecommendedInsurancePriceCustom.setText(mModel.getsRecommendedPrice());
                }
                break;
            case R.id.frg_recommended_insurance_set_continue:
                if (isValid()) {
                    ((SendFragment) getParentFragment()).addFragment(new ConfirmSwishFragment(), true);
                }
                break;
        }
    }

    private boolean isValid() {
        if (mModel.getsRecommendedPriceValue() < 8) {
            mBinding.frgRecommendedInsurancePriceCustom.requestFocus();
            showSnackBar(mBinding, getString(R.string.e_please_enter_amount_grater_than_8));
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        mModel.setsRecommendedPriceAndSetRewardPrice(8);
        if (mSubscriptionCustomPrice != null)
            mSubscriptionCustomPrice.unsubscribe();
        super.onDestroy();
    }

    private void setTotalPrice() {
        mBinding.frgRecommendedInsurancePrice.setText(
                getString(R.string.euro) + String.valueOf(mModel.getsRecommendedPrice()));
    }

    private void setVat() {
        mBinding.frgRecommendedVat.rowRecommendedText.setText(getString(R.string.vat));
        mBinding.frgRecommendedVat.rowRecommendedPrice.setText(
                getString(R.string.euro) + SPACE + mModel.getVatString());
    }

    private void setInsuranceAndFee() {
        mBinding.frgRecommendedInsuranceFee.rowRecommendedText.setText(getString(R.string.insurance_and_service_fee));
        mBinding.frgRecommendedInsuranceFee.rowRecommendedPrice.setText(
                getString(R.string.euro) + SPACE + mModel.getTotalFee());
    }

    private void setRewards() {
        mBinding.frgRecommendedRewards.rowRecommendedText.setText(getString(R.string.swishrs_rewards));
        mBinding.frgRecommendedRewards.rowRecommendedPrice.setText(
                getString(R.string.euro) + SPACE + mModel.getsRewardPriceString());
    }

}
