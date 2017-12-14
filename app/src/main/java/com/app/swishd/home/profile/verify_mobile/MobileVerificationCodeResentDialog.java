package com.app.swishd.home.profile.verify_mobile;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseDialogFragment;
import com.app.swishd.databinding.DialogMobileVerificationCodeResentBinding;


@SuppressLint("ValidFragment")
public class MobileVerificationCodeResentDialog extends BaseDialogFragment {
    private DialogMobileVerificationCodeResentBinding mBinding;
    private String mobileNo;

    public MobileVerificationCodeResentDialog() {

    }

    public MobileVerificationCodeResentDialog(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_mobile_verification_code_resent, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.tvMobileNumber.setText(mobileNo);
        mBinding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}