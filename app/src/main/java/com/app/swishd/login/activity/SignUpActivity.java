package com.app.swishd.login.activity;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.databinding.ActSignUpBinding;
import com.app.swishd.login.model.ResponseSuccess;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.retrofit.RequestCode;
import com.app.swishd.utility.Utility;
import com.jakewharton.rxbinding.widget.RxTextView;

import io.reactivex.observers.DisposableObserver;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class SignUpActivity extends SocialLoginActivity
        implements View.OnClickListener, OnResponseCallback {

    private ActSignUpBinding mBinding;
    private DisposableObserver mCall;
    private DisposableObserver mCallCheckUserName;
    private Subscription mUsernameSubscription;
    private boolean isUsernameValid;

    @Override
    public int getLayout() {
        return R.layout.act_sign_up;
    }

    @Override
    public void setBinding(ViewDataBinding bindingObject) {
        mBinding = (ActSignUpBinding) bindingObject;
    }

    @Override
    public void init() {
        setToolbar();
        setOnClickListener();
        setOnTextChangeListener();
    }

    private void setOnTextChangeListener() {
        mUsernameSubscription = RxTextView.textChanges(mBinding.actSignUpUsername)
                .map(new Func1<CharSequence, String>() {
                    @Override
                    public String call(CharSequence charSequence) {
                        return charSequence.toString().trim();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String charSequence) {
                        if (!charSequence.isEmpty()) {
                            doCheckUsername(charSequence);
                        } else {
                            unSubscribeUserCallback();
                            isUsernameValid = false;
                            mBinding.actSignUpUsernameValidIcon.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private void setToolbar() {
        setStatusBarColor(android.R.color.white);
        mBinding.actSignUpToolbar.toolbarTitle.setText(getString(R.string.sign_up));
    }

    private void setOnClickListener() {
        mBinding.actSignUpToolbar.toolbarBackArrow.setOnClickListener(this);
        mBinding.actSignUp.setOnClickListener(this);
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
            case R.id.act_sign_up:
                if (isValid()) {
                    doCheckEmail();
                }
                break;
        }
    }

    private void doCheckEmail() {
        showProgressDialog();
        hideKeyboard();
        mCall = getApiTask().doCheckEmail(this,
                getString(mBinding.actSignUpEmail),
                this);
    }

    private void doCheckUsername(String userName) {
        unSubscribeUserCallback();

        mCallCheckUserName = getApiTask()
                .doCheckUsername(this, userName, new OnResponseCallback() {
                    @Override
                    public void onResponseReceived(Object model, int requestCode) {
                        isUsernameValid = true;
                        mBinding.actSignUpUsernameValidIcon.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponseError(String message) {
                        showSnackbar(mBinding, message);
                        isUsernameValid = false;
                        mBinding.actSignUpUsernameValidIcon.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onResponseCompleted() {

                    }

                    @Override
                    public void onTokenExpires() {

                    }
                });
    }

    private void unSubscribeUserCallback() {
        if (mCallCheckUserName != null) {
            mCallCheckUserName.dispose();
        }
    }

    private void doRegister() {
        mCall = getApiTask().doSignUp(this,
                getString(mBinding.actSignUpUsername),
                getString(mBinding.actSignUpEmail),
                getString(mBinding.actSignUpPassword),
                getString(mBinding.actSignUpFirstName),
                getString(mBinding.actSignUpLastName),
                getString(mBinding.actSignUpPromoCode),
                this
        );
    }

    private boolean isValid() {

        if (getString(mBinding.actSignUpFirstName).isEmpty()) {
            showSnackbar(mBinding, getString(R.string.e_please_enter_first_name));
            mBinding.actSignUpFirstName.requestFocus();
            return false;
        } else if (getString(mBinding.actSignUpLastName).isEmpty()) {
            showSnackbar(mBinding, getString(R.string.e_please_enter_last_name));
            mBinding.actSignUpLastName.requestFocus();
            return false;
        } else if (getString(mBinding.actSignUpEmail).isEmpty()) {
            showSnackbar(mBinding, getString(R.string.e_please_enter_email));
            mBinding.actSignUpEmail.requestFocus();
            return false;
        } else if (!Utility.isEmailValid(getString(mBinding.actSignUpEmail))) {
            showSnackbar(mBinding, getString(R.string.e_please_enter_valid_email_address));
            mBinding.actSignUpEmail.requestFocus();
            return false;
        } else if (getString(mBinding.actSignUpUsername).isEmpty()) {
            showSnackbar(mBinding, getString(R.string.e_please_enter_username));
            mBinding.actSignUpUsername.requestFocus();
            return false;
        } else if (getString(mBinding.actSignUpPassword).isEmpty()) {
            showSnackbar(mBinding, getString(R.string.e_please_enter_password));
            mBinding.actSignUpPassword.requestFocus();
            return false;
        } else if (getString(mBinding.actSignUpPassword).length() < 6) {
            showSnackbar(mBinding, getString(R.string.e_passowrd_must_contains_at_least_6_character));
            mBinding.actSignUpPassword.requestFocus();
            return false;
        } else if (!isUsernameValid) {
            showSnackbar(mBinding, getString(R.string.e_please_use_valid_username));
            mBinding.actSignUpUsername.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onResponseReceived(Object responseModel, int requestCode) {
        if (requestCode == RequestCode.REQUEST_SIGN_UP) {
            hideProgressDialog();
            ResponseSuccess model = (ResponseSuccess) responseModel;
            if (model != null)
                toast(model.getMessage());
            onBackPressed();
        } else if (requestCode == RequestCode.REQUEST_CHECK_EMAIL) {
            doRegister();
        }
    }

    @Override
    public void onResponseError(String message) {
        hideProgressDialog();
        showSnackbar(mBinding, message);
    }

    @Override
    public void onTokenExpires() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null)
            mCall.dispose();
        unSubscribeUserCallback();
        if (mUsernameSubscription != null)
            mUsernameSubscription.unsubscribe();
        mBinding = null;
    }

}
