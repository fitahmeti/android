package com.app.swishd.home.profile.verify_id;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseDialogFragment;
import com.app.swishd.databinding.DialogImageBinding;
import com.app.swishd.utility.ImageUtil;


@SuppressLint("ValidFragment")
public class ImageDialog extends BaseDialogFragment {
    private DialogImageBinding mBinding;
    private String imagePath;

    private ImageDialog() {
    }

    public ImageDialog(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_image, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageUtil.load(imagePath, mBinding.imageView);
        mBinding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
