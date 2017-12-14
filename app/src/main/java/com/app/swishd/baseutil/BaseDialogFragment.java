package com.app.swishd.baseutil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.app.swishd.R;
import com.app.swishd.retrofit.call.ApiTask;
import com.app.swishd.utility.MyPrefs;


public abstract class BaseDialogFragment extends DialogFragment {


    private BaseFragmentActivity activity;

    public BaseDialogFragment() {
    }

    protected abstract View getLayout(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
        activity = (BaseFragmentActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getDialog().getWindow().setGravity(Gravity.CENTER);
        return getLayout(inflater, container);
    }

    public void toast(int resMsg) {
        if (activity != null)
            activity.toast(resMsg);
    }

    public void toast(String msg) {
        if (activity != null)
            activity.toast(msg);
    }

    public void showProgressDialog() {
        if (activity != null)
            activity.showProgressDialog();
    }

    public void dismissProgressDialog() {
        if (activity != null)
            activity.hideProgressDialog();
    }

    public ApiTask getApiTask() {
        return ApiTask.getInstance();
    }

    public MyPrefs getPrefs() {
        return MyPrefs.getInstance();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            if (!isAdded())
                super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}