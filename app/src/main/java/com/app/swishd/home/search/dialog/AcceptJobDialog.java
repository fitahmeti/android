package com.app.swishd.home.search.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.app.swishd.R;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.retrofit.call.ApiTask;
import com.app.swishd.utility.Utility;

public class AcceptJobDialog extends DialogFragment
        implements DialogInterface.OnClickListener, OnResponseCallback {

    private String mScanId;
    private boolean isMyJob;

    public static AcceptJobDialog getInstance(String scanId, boolean isMyJob) {
        AcceptJobDialog mDialog = new AcceptJobDialog();
        mDialog.mScanId = scanId;
        mDialog.isMyJob = isMyJob;
        return mDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScanId == null || mScanId.isEmpty()) {
            dismiss();
            return;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.job));
        if (!isMyJob) {
            builder.setMessage(getString(R.string.accept_dailog_message));
            builder.setPositiveButton(getString(R.string.accept), this);
        }else {
            builder.setMessage(
                    getString(R.string.you_are_not_a_swishr_or_collection_point_of_this_job));
            builder.setNegativeButton(getString(R.string.cancel), null);
        }

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        doConfirmJob();
    }

    public void doConfirmJob() {
        ApiTask.getInstance().doScanConfirmJob(
                getContext(), mScanId, this
        );
    }

    @Override
    public void onResponseReceived(Object model, int requestCode) {
        dismiss();
    }

    @Override
    public void onResponseError(String message) {
        Utility.showToast(getContext(), message);
    }

    @Override
    public void onResponseCompleted() {
        dismiss();
    }

    @Override
    public void onTokenExpires() {
        dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScanId = null;
    }
}
