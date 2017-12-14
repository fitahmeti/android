package com.app.swishd.home.profile.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.DialogAcceptJobBinding;
import com.app.swishd.retrofit.call.Api;
import com.app.swishd.utility.ImageUtil;

public class AcceptOfferDailog extends DialogFragment implements View.OnClickListener {

    private String mName;
    private String mUrl;
    private AcceptOfferCallback mCallback;
    private DialogAcceptJobBinding mBinding;

    public static AcceptOfferDailog getInstance(String name, String url, AcceptOfferCallback callback) {
        AcceptOfferDailog dialog = new AcceptOfferDailog();
        dialog.mName = name;
        dialog.mUrl = url;
        dialog.mCallback = callback;
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mName == null || mUrl == null || mCallback == null)
            dismiss();
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_accept_job, container,
                false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnClickListener();
        ImageUtil.load(Api.BASE_URL_IMAGE + mUrl, mBinding.dialogAcceptSwishrImage);
        mBinding.dialogAcceptSwishrName.setText(mName);
    }

    private void setOnClickListener() {
        mBinding.dialogAcceptCancel.setOnClickListener(this);
        mBinding.dialogAcceptAccept.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_accept_accept:
                mCallback.onAcceptOffer();
                dismiss();
                break;
            case R.id.dialog_accept_cancel:
                dismiss();
                break;
        }
    }

    public interface AcceptOfferCallback {
        void onAcceptOffer();
    }

}
