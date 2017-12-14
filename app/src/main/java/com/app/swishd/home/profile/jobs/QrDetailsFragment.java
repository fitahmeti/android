package com.app.swishd.home.profile.jobs;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgQrDetailsBinding;
import com.app.swishd.retrofit.call.Api;
import com.app.swishd.utility.ImageUtil;

public class QrDetailsFragment extends BaseFragment {

    private FrgQrDetailsBinding mBinding;
    private String mUrl;
    private String qrCode;

    public static QrDetailsFragment getInstance(String url, String qrCode) {
        QrDetailsFragment fragment = new QrDetailsFragment();
        fragment.mUrl = url;
        fragment.qrCode = qrCode;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mUrl == null && qrCode == null) {
            toast("Please use getInstance() method to create object");
            getActivity().onBackPressed();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.frg_qr_details;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgQrDetailsBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBinding.frgBarcodeText.setText(qrCode);
        ImageUtil.load(Api.BASE_URL_IMAGE + mUrl,
                mBinding.frgBarcodeImage,
                R.drawable.ic_place_holder);
        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

}
