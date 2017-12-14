package com.app.swishd.home.search.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.widget.TextView;

import com.app.swishd.R;

import java.util.Locale;

public class AcceptJobOfferDialog extends DialogFragment
        implements DialogInterface.OnClickListener {

    private String mScanName;
    private AcceptJobOfferCallback mCallback;

    public static AcceptJobOfferDialog getInstance(String name, AcceptJobOfferCallback callback) {
        AcceptJobOfferDialog mDialog = new AcceptJobOfferDialog();
        mDialog.mScanName = name;
        mDialog.mCallback = callback;
        return mDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScanName == null || mScanName.isEmpty()) {
            dismiss();
            return;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.aceept_job_offer));
        builder.setMessage(
                String.format(Locale.getDefault(),
                        getString(R.string.aceept_job_offer_message),
                        mScanName, mScanName));
        builder.setPositiveButton(getString(R.string.ok), this);

        TextView title = new TextView(getContext());
        title.setText(getString(R.string.aceept_job_offer));
        title.setPadding(10, 25, 10, 25);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        title.setTypeface(null, Typeface.BOLD);
        builder.setCustomTitle(title);

        AlertDialog dialog = builder.show();
        TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

        setCancelable(false);
        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mCallback != null)
            mCallback.onAcceptJobOk();
        dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScanName = null;
        mCallback = null;
    }
}
