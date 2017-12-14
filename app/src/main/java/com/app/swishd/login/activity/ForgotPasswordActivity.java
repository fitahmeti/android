package com.app.swishd.login.activity;

import android.databinding.ViewDataBinding;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragmentActivity;
import com.app.swishd.databinding.ActForgotPasswordBinding;
import com.app.swishd.login.model.ResponseSuccess;
import com.app.swishd.utility.MyPrefs;
import com.app.swishd.utility.Utility;

import com.app.swishd.retrofit.OnResponseCallback;

public class ForgotPasswordActivity
        extends BaseFragmentActivity implements View.OnClickListener, OnResponseCallback<ResponseSuccess> {

    private ActForgotPasswordBinding mBinding;
    private DisposableObserver mCall;

    @Override
    public int getLayout() {
        return R.layout.act_forgot_password;
    }

    @Override
    public void setBinding(ViewDataBinding bindingObject) {
        mBinding = (ActForgotPasswordBinding) bindingObject;
    }

    @Override
    public void init() {
        setToolbar();
        setOnClickListener();
    }

    private void setToolbar() {
        setStatusBarColor(android.R.color.white);
        mBinding.actForgotPasswordToolbar.toolbarTitle
                .setText(getString(R.string.title_forgot_password));
    }

    private void setOnClickListener() {
        mBinding.actForgotPasswordToolbar.toolbarBackArrow.setOnClickListener(this);
        mBinding.actForgotPasswordResetPassword.setOnClickListener(this);
    }

    @Override
    public void finish() {
        super.finish();
        exitHorizontal();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back_arrow:
                onBackPressed();
                break;
            case R.id.act_forgot_password_reset_password:
                if (isValid()) {
                    doForgotPassword();
                }
                break;
        }
    }

    private void doForgotPassword() {
        hideKeyboard();
        showProgressDialog();
        mCall = getApiTask().doForgotPassword(this, getString(mBinding.actForgotPasswordEmail),
                                              this);
    }

    private boolean isValid() {
        if (getString(mBinding.actForgotPasswordEmail).isEmpty()) {
            showSnackbar(mBinding, getString(R.string.e_please_enter_email));
            mBinding.actForgotPasswordEmail.requestFocus();
            return false;
        } else if (!Utility.isEmailValid(getString(mBinding.actForgotPasswordEmail))) {
            showSnackbar(mBinding, getString(R.string.e_please_enter_valid_email_address));
            mBinding.actForgotPasswordEmail.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onResponseReceived(ResponseSuccess model, int requestCode) {
        hideKeyboard();
        hideProgressDialog();
        if (model != null)
            toast(model.getMessage());
        onBackPressed();
    }

    @Override
    public void onResponseError(String message) {
        hideKeyboard();
        hideProgressDialog();
        showSnackbar(mBinding, message);
    }

    @Override
    public void onTokenExpires() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null)
            mCall.dispose();
        mBinding = null;
    }
}
