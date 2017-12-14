package com.app.swishd.home.profile.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.DialogCongratulationSwishrBinding;
import com.app.swishd.retrofit.call.Api;
import com.app.swishd.utility.ImageUtil;

import java.util.Locale;

import static com.app.swishd.utility.Utility.getBoldString;

public class CongratulationSwishrDailog extends DialogFragment implements View.OnClickListener {

    private String mName;
    private String mUrl;
    private CongratulationSwishrDailogCallback mCallback;
    private DialogCongratulationSwishrBinding mBinding;

    public static CongratulationSwishrDailog getInstance(String name, String url,
                                                         CongratulationSwishrDailogCallback callback) {
        CongratulationSwishrDailog dialog = new CongratulationSwishrDailog();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_congratulation_swishr, container,
                false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnClickListener();
        ImageUtil.load(Api.BASE_URL_IMAGE + mUrl, mBinding.dialogCongratulationSwishrImage);
        mBinding.dialogCongratulationSwishrName.setText(mName);
        mBinding.dialogCongratulationUpperText.setText(
                getBoldString(String.format(
                        Locale.getDefault(),
                        getString(R.string.you_have_accpted_as_your_swishr_for_this_item),
                        mName), mName));
        
        String bottomText = String.format(
                Locale.getDefault(),
                getString(R.string.you_can_view_item_activity_and_contact_on_your_item_page),
                mName);
        mBinding.dialogCongratulationBottomText.setText(
                getBoldString(bottomText, mName)
        );
    }

    private void setOnClickListener() {
        mBinding.dialogCongratulationCancel.setOnClickListener(this);
        mBinding.dialogCongratulationAccept.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_congratulation_accept:
                mCallback.onViewActivity();
                dismiss();
                break;
            case R.id.dialog_congratulation_cancel:
                dismiss();
                break;
        }
    }

    public interface CongratulationSwishrDailogCallback {
        void onViewActivity();
    }
}
