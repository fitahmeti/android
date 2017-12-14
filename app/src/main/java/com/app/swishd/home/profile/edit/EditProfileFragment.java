package com.app.swishd.home.profile.edit;


import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgEditProfileBinding;
import com.app.swishd.home.profile.ProfileFragment;
import com.app.swishd.home.profile.model.EditProfileModel;
import com.app.swishd.home.profile.model.UserProfile;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;
import com.app.swishd.retrofit.call.Api;
import com.app.swishd.utility.EnumPreference;
import com.app.swishd.utility.ImageUtil;
import com.vinay.utillib.UtilLib;
import com.vinay.utillib.imagechooser.ChooseType;
import com.vinay.utillib.imagechooser.OnImageChooserListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends BaseFragment {

    private FrgEditProfileBinding mBinding;
    private String imageProfile;
    private UserProfile profileData;

    @Override
    public int getLayout() {
        return R.layout.frg_edit_profile;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgEditProfileBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragment().getChildFragmentManager().popBackStack();
            }
        });
        displayProfile();

        mBinding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilLib.getPhoto(getContext(), ChooseType.REQUEST_ANY).enqueue(new OnImageChooserListener() {
                    @Override
                    public void onImageChoose(String s) {
                        imageProfile = s;
                        ImageUtil.load(imageProfile, mBinding.imgProfile);
                    }
                });
            }
        });

        mBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    showProgressDialog();
                    getApiTask().editProfile(
                            mBinding.edtUsername.getText().toString().trim(),
                            mBinding.edtFirstName.getText().toString().trim(),
                            mBinding.edtLastName.getText().toString().trim(),
                            mBinding.edtEmail.getText().toString().trim(),
                            imageProfile,
                            callbackEditProfile
                    );
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("Tag", "UserVisibleHint: " + isVisibleToUser);
    }

    private void displayProfile() {
        profileData = UserProfile.fromString(getPrefs().getStringData(EnumPreference.MY_PROFILE));
        mBinding.setProfile(profileData);
        if (profileData.getProfileImage() != null && !profileData.getProfileImage().isEmpty())
            ImageUtil.load(Api.BASE_URL_IMAGE + profileData.getProfileImage(), mBinding.imgProfile, R.drawable.ic_person);
    }

    private boolean isValid() {
        hideKeyboard();
        String username = mBinding.edtUsername.getText().toString().trim();
        if (username.isEmpty()) {
            showSnackBar(getString(R.string.e_please_enter_username));
            return false;
        }
        return true;
    }

    private Callback<EditProfileModel> callbackEditProfile = new Callback<EditProfileModel>() {
        @Override
        public void onResponse(Call<EditProfileModel> call, Response<EditProfileModel> response) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                if (response.body() != null && response.body().getUserProfileList() != null && response.body().getUserProfileList().size() > 0) {
                    profileData.setEmail(response.body().getUserProfileList().get(0).getEmail());
                    profileData.setFirstName(response.body().getUserProfileList().get(0).getFirstName());
                    profileData.setLastName(response.body().getUserProfileList().get(0).getLastName());
                    profileData.setUsername(response.body().getUserProfileList().get(0).getUsername());
                    profileData.setProfileImage(response.body().getUserProfileList().get(0).getProfileImage());
                    getPrefs().putData(EnumPreference.MY_PROFILE, profileData.toString());
                    getTargetFragment().onActivityResult(ProfileFragment.RC_EDIT_PROFILE, Activity.RESULT_OK, null);
                    getParentFragment().getChildFragmentManager().popBackStack();
                }
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                showSnackBar(error.getError());
            }
        }

        @Override
        public void onFailure(Call<EditProfileModel> call, Throwable t) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            showSnackBar(t.getMessage());
        }
    };
}
