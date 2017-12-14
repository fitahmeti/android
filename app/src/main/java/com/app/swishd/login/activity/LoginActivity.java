package com.app.swishd.login.activity;

import android.databinding.ViewDataBinding;
import android.os.Build;
import android.view.View;

import com.app.swishd.BuildConfig;
import com.app.swishd.R;
import com.app.swishd.databinding.ActLoginBinding;
import com.app.swishd.home.MainActivity;
import com.app.swishd.login.model.ResponseLogin;
import com.app.swishd.retrofit.OnResponseCallback;

import io.reactivex.observers.DisposableObserver;

import static com.app.swishd.utility.EnumPreference.Authorization;

public class LoginActivity extends SocialLoginActivity
        implements View.OnClickListener, OnResponseCallback<ResponseLogin> {

    private ActLoginBinding mBinding;
    private DisposableObserver mCall;

    @Override
    public int getLayout() {
        return R.layout.act_login;
    }

    @Override
    public void setBinding(ViewDataBinding bindingObject) {
        mBinding = (ActLoginBinding) bindingObject;
    }

    @Override
    public void init() {
        setToolbar();
        setOnClickListener();

        if(BuildConfig.DEBUG){
            mBinding.actLoginUsername.setText("dWalton");
            mBinding.actLoginPassword.setText("123456");
        }
    }

    private void setToolbar() {
        setStatusBarColor(android.R.color.white);
        mBinding.actLoginToolbar.toolbarTitle.setText(getString(R.string.title_login));
    }

    private void setOnClickListener() {
        mBinding.actLoginForgotPassword.setOnClickListener(this);
        mBinding.actLoginToolbar.toolbarBackArrow.setOnClickListener(this);
        mBinding.actLogin.setOnClickListener(this);
    }

    @Override
    public void finish() {
        super.finish();
        exitHorizontal();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_login_forgot_password:
                navigateActivity(this, ForgotPasswordActivity.class, false);
                enterHorizontal();
                break;
            case R.id.toolbar_back_arrow:
                onBackPressed();
                break;
            case R.id.act_login:
                if (isValid()) {
                    doLogin();
                }
                break;
        }
    }

    private void doLogin() {
        hideKeyboard();
        showProgressDialog();
        mCall = getApiTask().doLogin(this,
                getString(mBinding.actLoginUsername),
                getString(mBinding.actLoginPassword),
                this
        );
    }

    private boolean isValid() {
        if (getString(mBinding.actLoginUsername).isEmpty()) {
            showSnackbar(mBinding, getString(R.string.e_please_enter_email));
            mBinding.actLoginUsername.requestFocus();
            return false;
        } else if (getString(mBinding.actLoginPassword).isEmpty()) {
            showSnackbar(mBinding, getString(R.string.e_please_enter_password));
            mBinding.actLoginPassword.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onResponseReceived(ResponseLogin model, int requestCode) {
        hideProgressDialog();
        if (model != null)
            getPrefs().putData(Authorization,
                    model.getAuthorization());
        navigateActivity(this, MainActivity.class, true);
    }

    @Override
    public void onResponseError(String message) {
        hideProgressDialog();
        toast(message);
    }

    @Override
    public void onTokenExpires() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null)
            mCall.dispose();
        mBinding = null;
    }

}
