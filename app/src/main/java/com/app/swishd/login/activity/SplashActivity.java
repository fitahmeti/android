package com.app.swishd.login.activity;

import android.databinding.ViewDataBinding;
import android.os.CountDownTimer;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragmentActivity;
import com.app.swishd.home.MainActivity;

public class SplashActivity extends BaseFragmentActivity {

    @Override
    public int getLayout() {
        return R.layout.act_splash;
    }

    @Override
    public void setBinding(ViewDataBinding bindingObject) {
    }

    @Override
    public void init() {
        splashTimer.start();
    }

    private CountDownTimer splashTimer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            if (getPrefs().isLogin())
                navigateActivity(SplashActivity.this, MainActivity.class, true);
            else
                navigateActivity(SplashActivity.this, LoginHelpActivity.class, true);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        splashTimer.cancel();
        finish();
    }
}
