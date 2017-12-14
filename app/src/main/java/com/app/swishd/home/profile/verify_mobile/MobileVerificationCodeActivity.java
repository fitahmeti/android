package com.app.swishd.home.profile.verify_mobile;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.app.swishd.R;
import com.app.swishd.databinding.ActMobileVerificationCodeBinding;
import com.app.swishd.home.profile.model.UserProfile;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;
import com.app.swishd.utility.EnumPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileVerificationCodeActivity extends BaseMobileVerificationActivity {
    private ActMobileVerificationCodeBinding mBinding;

    @Override
    public int getLayout() {
        return R.layout.act_mobile_verification_code;
    }

    @Override
    public void setBinding(ViewDataBinding bindingObject) {
        mBinding = (ActMobileVerificationCodeBinding) bindingObject;
    }

    @Override
    public void init() {

        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.btnEditNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBinding.btnResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.edtCode.setText("");
                resendVerificationCode();
            }
        });

        mBinding.btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhoneNumberWithCode(mBinding.edtCode.getText().toString());
            }
        });

        mBinding.tvMobileNumber.setText(getMobileNumber());

        final ArrayList<TextView> tvCodes = new ArrayList<>();
        tvCodes.add(mBinding.tvCode1);
        tvCodes.add(mBinding.tvCode2);
        tvCodes.add(mBinding.tvCode3);
        tvCodes.add(mBinding.tvCode4);
        tvCodes.add(mBinding.tvCode5);
        tvCodes.add(mBinding.tvCode6);
        mBinding.edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int codeCounter = 0;
                for (; codeCounter < s.length(); codeCounter++) {
                    tvCodes.get(codeCounter).setText("" + s.charAt(codeCounter));
                }
                for (; codeCounter < tvCodes.size(); codeCounter++) {
                    tvCodes.get(codeCounter).setText("");
                }

                mBinding.btnEnter.setEnabled(mBinding.edtCode.getText().toString().length() == 6);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mBinding.viewCodeFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.edtCode.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(mBinding.edtCode, 0);
            }
        });
    }

    @Override
    public void onVerificationCodeSent() {
        MobileVerificationCodeResentDialog resentDialog = new MobileVerificationCodeResentDialog(getMobileNumber());
        resentDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onSuccessVerification() {
        showProgressDialog();
        getApiTask().verifyMobile(getMobileNumber(), getCountryCode(), callbackVerifyMobile);
    }

    private Callback<Object> callbackVerifyMobile = new Callback<Object>() {
        @Override
        public void onResponse(Call<Object> call, Response<Object> response) {
            hideProgressDialog();
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                UserProfile profileData = UserProfile.fromString(getPrefs().getStringData(EnumPreference.MY_PROFILE));
                profileData.setMobileVerify(Long.valueOf(1));
                profileData.setMobile(getMobileNumber());
                profileData.setCountryCode(getCountryCode());
                getPrefs().putData(EnumPreference.MY_PROFILE, profileData.toString());
                setResult(RESULT_OK);
                finish();
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                toast(error.getError());
            }
        }

        @Override
        public void onFailure(Call<Object> call, Throwable t) {
            hideProgressDialog();
            if (call.isCanceled())
                return;
            toast(t.getMessage());
        }
    };
}
