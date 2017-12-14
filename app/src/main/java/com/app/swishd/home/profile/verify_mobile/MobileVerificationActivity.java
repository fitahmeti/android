package com.app.swishd.home.profile.verify_mobile;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.util.Log;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragmentActivity;
import com.app.swishd.databinding.ActMobileVerificationBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MobileVerificationActivity extends BaseMobileVerificationActivity {
    private static final int RC_COUNTRY_CODE = 1453;
    private ActMobileVerificationBinding mBinding;
    private static final int REQUEST_CODE_VERIFY_MOBILE = 2236;

    @Override
    public int getLayout() {
        return R.layout.act_mobile_verification;
    }

    @Override
    public void setBinding(ViewDataBinding bindingObject) {
        mBinding = (ActMobileVerificationBinding) bindingObject;
    }

    @Override
    public void init() {

        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidMobileNumber()) {
                    sendVerificationCode(mBinding.tvCountryCode.getText().toString() , mBinding.edtMobile.getText().toString().trim());
                }
            }
        });

        mBinding.tvCountryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MobileVerificationActivity.this, CountryCodeActivity.class), RC_COUNTRY_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VERIFY_MOBILE) {
            if (resultCode == Activity.RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        } else if (requestCode == RC_COUNTRY_CODE) {
            if (resultCode == RESULT_OK) {
                mBinding.tvCountryCode.setText(data.getStringExtra(CountryCodeActivity.INTENT_EXTRA_COUNTRY_CODE));
            }
        }
    }

    private boolean isValidMobileNumber() {
        hideKeyboard();
        String mobileNo = mBinding.edtMobile.getText().toString().trim();
        if (mobileNo.isEmpty()) {
            showSnackBar("Please enter mobile number");
            mBinding.edtMobile.requestFocus();
            return false;
        }
        mobileNo = mBinding.tvCountryCode.getText().toString() + mobileNo;
        if (!mobileNo.matches("^[+]?[0-9]{10,13}$")) {
            showSnackBar("Invalid number");
            mBinding.edtMobile.requestFocus();
            return false;
        }
        return true;
    }


    @Override
    public void onVerificationCodeSent() {
        startActivityForResult(
                new Intent(MobileVerificationActivity.this, MobileVerificationCodeActivity.class)
//                        .putExtra("mobile", mBinding.tvCountryCode.getText().toString().trim() + mBinding.edtMobile.getText().toString().trim())
                , REQUEST_CODE_VERIFY_MOBILE);
    }

    @Override
    public void onSuccessVerification() {

    }
}
