package com.app.swishd.baseutil;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.swishd.retrofit.call.ApiTask;
import com.app.swishd.utility.MyPrefs;
import com.app.swishd.utility.Utility;
import com.facebook.login.Login;

public abstract class BaseFragment extends Fragment {

    private BaseFragmentActivity activity;
    private View rootView;

    public abstract int getLayout();

    public abstract void setBinding(ViewDataBinding binding);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        setBinding(dataBinding);
        rootView = dataBinding.getRoot();
        return dataBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseFragmentActivity) getActivity();
    }

    public void toast(int resMsg) {
        if (activity != null)
            activity.toast(resMsg);
    }

    public void toast(String msg) {
        if (activity != null)
            activity.toast(msg);
    }

    public void showSnackBar(@NonNull String message) {
        if (activity != null)
            activity.showSnackBar(rootView, message, Snackbar.LENGTH_SHORT);
    }

    public void showSnackBar(ViewDataBinding mBindng, String msg) {
        if (activity != null)
            activity.showSnackbar(mBindng, msg);
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

    public void onResponseCompleted() {

    }

    public void onTokenExpires() {
        dismissProgressDialog();
        getPrefs().clearPrefrences();
        activity.navigateActivity(activity, Login.class, true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (activity != null)
            activity.hideKeyboard();
    }

    public String getString(EditText editText) {
        return activity.getString(editText);
    }

    public BaseFragmentActivity getHomeActivity(){
        return activity;
    }

    public void hideKeyboard() {
        Utility.hideKeyboard(getActivity());
    }
}
