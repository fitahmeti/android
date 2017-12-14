package com.app.swishd.home.profile.more;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgMoreBinding;
import com.app.swishd.retrofit.call.ApiTask;
import com.app.swishd.utility.MyPrefs;
import com.app.swishd.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoreFragment extends BaseFragment implements View.OnClickListener {
    private FrgMoreBinding mBinding;

    @Override
    public int getLayout() {
        return R.layout.frg_more;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgMoreBinding) binding;
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
        mBinding.btnLogout.setOnClickListener(logoutClickListener);

        mBinding.btnAboutUs.setOnClickListener(this);
        mBinding.btnGetInTouch.setOnClickListener(this);
        mBinding.btnHelp.setOnClickListener(this);
        mBinding.btnPrivacyPolicy.setOnClickListener(this);
        mBinding.btnReferFriend.setOnClickListener(this);
        mBinding.btnTermsCondition.setOnClickListener(this);
        mBinding.btnNotificationSettings.setOnClickListener(this);
    }

    private View.OnClickListener logoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showProgressDialog();
            ApiTask.getInstance().logout(callbackLogout);
        }
    };

    private Callback<Object> callbackLogout = new Callback<Object>() {
        @Override
        public void onResponse(Call<Object> call, Response<Object> response) {
            dismissProgressDialog();
            if (!call.isCanceled() && response.isSuccessful())
                MyPrefs.getInstance().logout();
        }

        @Override
        public void onFailure(Call<Object> call, Throwable t) {
            dismissProgressDialog();
            if (!call.isCanceled())
                toast(t.getMessage());
        }
    };

    @Override
    public void onClick(View v) {
        if (mBinding.btnAboutUs == v) {
            Utility.openWebPage(getContext(), "http://www.google.com");
        } else if (mBinding.btnHelp == v) {
            Utility.openWebPage(getContext(), "http://www.google.com");
        } else if (mBinding.btnTermsCondition == v) {
            Utility.openWebPage(getContext(), "http://www.google.com");
        } else if (mBinding.btnPrivacyPolicy == v) {
            Utility.openWebPage(getContext(), "http://www.google.com");
        } else if (mBinding.btnGetInTouch == v) {
            Utility.composeEmail(getContext(), new String[]{"tony@gmail.com"}, "Test");
        } else if (mBinding.btnNotificationSettings == v) {
//            ((BaseContainerFragment) getParentFragment()).addFragment(new NotificationSettingsFragment(), true);
        } else if (mBinding.btnReferFriend == v) {
            ((BaseContainerFragment) getParentFragment()).addFragment(new ReferFriendFragment(), true);
        }
    }
}