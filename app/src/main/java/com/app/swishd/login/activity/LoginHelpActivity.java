package com.app.swishd.login.activity;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragmentActivity;
import com.app.swishd.databinding.ActLoginHelpBinding;


public class LoginHelpActivity
        extends BaseFragmentActivity implements View.OnClickListener {

    private ActLoginHelpBinding mBinding;

    @Override
    public int getLayout() {
        return R.layout.act_login_help;
    }

    @Override
    public void setBinding(ViewDataBinding bindingObject) {
        mBinding = (ActLoginHelpBinding) bindingObject;
    }

    @Override
    public void init() {
        setStatusBarColor(android.R.color.white);
        setOnClickListener();
    }

    private void setOnClickListener() {
        mBinding.actSplashLogIn.setOnClickListener(this);
        mBinding.actSplashSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_splash_log_in:
                navigateActivity(this, LoginActivity.class, false);
                enterHorizontal();
                break;
            case R.id.act_splash_sign_up:
                navigateActivity(this, SignUpActivity.class, false);
                enterHorizontal();
                break;
        }
    }
}
