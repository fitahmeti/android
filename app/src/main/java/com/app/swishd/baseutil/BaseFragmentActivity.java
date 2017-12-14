package com.app.swishd.baseutil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.swishd.R;
import com.app.swishd.retrofit.call.ApiTask;
import com.app.swishd.utility.MyPrefs;
import com.app.swishd.utility.Utility;


public abstract class BaseFragmentActivity extends FragmentActivity {

    private ApiTask apiTask;
    private ProgressDialog progressDialog;
    private MyPrefs mPrefs;
    private View rootView;

    public abstract int getLayout();

    public abstract void setBinding(ViewDataBinding bindingObject);

    public abstract void init();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, getLayout());
        rootView = binding.getRoot();
        setBinding(binding);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.loading_));
        progressDialog.setCancelable(false);

        apiTask = ApiTask.getInstance();
        mPrefs = MyPrefs.getInstance();
        init();
    }

    public MyPrefs getPrefs() {
        return mPrefs;
    }

    public ApiTask getApiTask() {
        return apiTask;
    }

    public void toast(int resMsg) {
        Toast.makeText(this, getString(resMsg), Toast.LENGTH_SHORT).show();
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(@NonNull String message) {
        showSnackBar(rootView, message, Snackbar.LENGTH_SHORT);
    }

    public void showSnackbar(@NonNull ViewDataBinding view, @NonNull String message) {
        if (view == null || view.getRoot() == null || message.isEmpty())
            return;
        showSnackBar(view.getRoot(), message, Snackbar.LENGTH_SHORT);
    }

    public void showSnackBar(View rootView, String text, int duration) {
        if (rootView == null ||text == null || text.isEmpty())
            return;

        Snackbar sb = Snackbar.make(rootView, text, duration);
        sb.setActionTextColor(getResources().getColor(android.R.color.white));
        View sbView = sb.getView();
        sbView.setBackgroundColor(Color.RED);
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        sb.show();
    }

    public void showProgressDialog() {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        try {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStatusBarColor(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(color));
        }
    }

    public String getString(EditText editText) {
        return editText.getText().toString().trim();
    }

    public void enterHorizontal() {
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.no_anim);
    }

    public void exitHorizontal() {
        overridePendingTransition(R.anim.no_anim, R.anim.push_out_to_right);
    }

    public void navigateActivity(Activity context, Class<?> className, boolean isFinish) {
        Intent iNavigateIntent = new Intent(context, className);
        startActivity(iNavigateIntent);
        if (isFinish)
            context.finishAffinity();
    }

    @Override
    public void finish() {
        super.finish();
        Utility.hideKeyboard(this);
    }

    public void onResponseCompleted() {
    }

    public void hideKeyboard() {
        Utility.hideKeyboard(this);
    }
}
