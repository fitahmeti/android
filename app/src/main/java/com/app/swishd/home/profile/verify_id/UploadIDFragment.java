package com.app.swishd.home.profile.verify_id;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgUploadIdBinding;
import com.app.swishd.home.profile.ProfileFragment;
import com.app.swishd.home.profile.model.ProofData;
import com.app.swishd.home.profile.model.UserProfile;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;
import com.app.swishd.utility.EnumPreference;
import com.app.swishd.utility.ImageUtil;
import com.vinay.utillib.UtilLib;
import com.vinay.utillib.imagechooser.ChooseType;
import com.vinay.utillib.imagechooser.OnImageChooserListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadIDFragment extends BaseFragment {
    private FrgUploadIdBinding mBinding;
    private String photoID;
    private String addressProof;

    @Override
    public int getLayout() {
        return R.layout.frg_upload_id;
    }

    @Override
    public void setBinding(ViewDataBinding bindingObject) {
        mBinding = (FrgUploadIdBinding) bindingObject;
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
                UtilLib.getPhoto(getContext(), ChooseType.REQUEST_ANY).enqueue(new OnImageChooserListener() {
                    @Override
                    public void onImageChoose(String s) {
                        photoID = s;
                        ImageUtil.load(photoID, mBinding.imgPhotoId);
                    }
                });
            }
        });

        mBinding.btnAddressProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilLib.getPhoto(getContext(), ChooseType.REQUEST_ANY).enqueue(new OnImageChooserListener() {
                    @Override
                    public void onImageChoose(String s) {
                        addressProof = s;
                        ImageUtil.load(addressProof, mBinding.imgAddressProof);
                    }
                });
            }
        });

        mBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    showProgressDialog();
                    getApiTask().uploadIDProof(photoID, addressProof, callbackUploadIDs);
                }
            }
        });
    }

    private boolean isValid() {
        hideKeyboard();
        if (photoID == null || addressProof == null || photoID.isEmpty() || addressProof.isEmpty()) {
            showSnackBar("Please add all proof.");
            return false;
        }
        return true;
    }


    private Callback<Object> callbackUploadIDs = new Callback<Object>() {
        @Override
        public void onResponse(Call<Object> call, Response<Object> response) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                UserProfile userData = UserProfile.fromString(getPrefs().getStringData(EnumPreference.MY_PROFILE));
                ProofData proofData = new ProofData();
                userData.setProofData(proofData);
                getPrefs().putData(EnumPreference.MY_PROFILE, userData.toString());

                getTargetFragment().onActivityResult(ProfileFragment.RC_UPLOAD_ID, Activity.RESULT_OK, null);
                ((BaseContainerFragment) getParentFragment()).popFragment();
                QrCodeFragment fragment = new QrCodeFragment();
                fragment.setTargetFragment(ProfileFragment.getInstance(), ProfileFragment.RC_QR_CODE);
                ((BaseContainerFragment) getParentFragment()).addFragment(fragment, true);
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                showSnackBar(error.getError());
            }
        }

        @Override
        public void onFailure(Call<Object> call, Throwable t) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            showSnackBar(t.getMessage());
        }
    };
}