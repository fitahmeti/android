package com.app.swishd;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.app.swishd.retrofit.RetrofitApp;
import com.app.swishd.utility.ImageUtil;
import com.app.swishd.utility.MyPrefs;

public class SwishdApp extends Application {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MyPrefs.init(this);
        RetrofitApp.initRetrofit(this);
        ImageUtil.init(this);
    }
}
