package com.app.swishd.home.profile.verify_id;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgVerificationQrBinding;
import com.app.swishd.home.profile.ProfileFragment;
import com.app.swishd.home.profile.model.UserProfile;
import com.app.swishd.home.profile.model.VerificationQRModel;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;
import com.app.swishd.utility.EnumPreference;
import com.app.swishd.utility.ImageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrCodeFragment extends BaseFragment {
    private FrgVerificationQrBinding mBinding;
    private UserProfile userData;

    @Override
    public int getLayout() {
        return R.layout.frg_verification_qr;
    }

    @Override
    public void setBinding(ViewDataBinding bindingObject) {
        mBinding = (FrgVerificationQrBinding) bindingObject;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mBinding.btnPhotoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDialog imageDialog = new ImageDialog(userData.getProofData().getIdProof());
                imageDialog.show(getChildFragmentManager(), "");
            }
        });
        mBinding.btnAddressProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDialog imageDialog = new ImageDialog(userData.getProofData().getAddressProof());
                imageDialog.show(getChildFragmentManager(), "");
            }
        });


        userData = UserProfile.fromString(getPrefs().getStringData(EnumPreference.MY_PROFILE));
        if (userData.getProofData().getScanCode() == null) {
            showProgressDialog();
            getApiTask().getIDProof(callbackIDProof);
        } else {showProofData();}
    }

    private Callback<VerificationQRModel> callbackIDProof = new Callback<VerificationQRModel>() {
        @Override
        public void onResponse(Call<VerificationQRModel> call, Response<VerificationQRModel> response) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                if (response.body().getProofDataList() != null && response.body().getProofDataList().size() > 0) {
                    userData.setProofData(response.body().getProofDataList().get(0));
                    getPrefs().putData(EnumPreference.MY_PROFILE, userData.toString());
                    getTargetFragment().onActivityResult(ProfileFragment.RC_QR_CODE, Activity.RESULT_OK, null);
                    showProofData();
                } else {
                    ((BaseContainerFragment) getParentFragment()).popFragment();
                }
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                toast(error.getError());
                ((BaseContainerFragment) getParentFragment()).popFragment();
            }
        }

        @Override
        public void onFailure(Call<VerificationQRModel> call, Throwable t) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            toast(t.getMessage());
            ((BaseContainerFragment) getParentFragment()).popFragment();
        }
    };

    private void showProofData() {
        ImageUtil.load(userData.getProofData().getQrImage(), mBinding.imgQrCode);
        ImageUtil.load(userData.getProofData().getIdProof(), mBinding.imgPhotoId);
        ImageUtil.load(userData.getProofData().getAddressProof(), mBinding.imgAddressProof);
        mBinding.tvQrCode.setText(userData.getProofData().getScanCode());
    }
}