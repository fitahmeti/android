package com.app.swishd.home.profile.more;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgReferFriendBinding;
import com.app.swishd.retrofit.OnResponseCallback;

public class ReferFriendFragment extends
        BaseFragment implements View.OnClickListener, OnResponseCallback {

    private FrgReferFriendBinding mBinding;
    private ReferFriendAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.frg_refer_friend;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgReferFriendBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setAdapter();
        setOnClickListener();
    }

    private void setOnClickListener() {
        mBinding.frgReferFriendInvite.setOnClickListener(this);
        mBinding.btnBack.setOnClickListener(this);
    }

    private void setAdapter() {
        mAdapter = new ReferFriendAdapter(getContext());
        mBinding.frgReferFriendList.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_refer_friend_invite:
                if (isValid())
                    doInviteFriend();
                break;
            case R.id.btn_back:
                getActivity().onBackPressed();
                break;
        }
    }

    private boolean isValid() {
        if (!mAdapter.checkValidEmail()) {
            showSnackBar(mBinding, getString(R.string.e_please_enter_valid_email_address));
            return false;
        }
        return true;
    }

    private void doInviteFriend() {
        showProgressDialog();
        getApiTask().doInviteFriend(getContext(), mAdapter.getEmailAddress(), this);
    }

    @Override
    public void onResponseReceived(Object model, int requestCode) {
        dismissProgressDialog();
        getActivity().onBackPressed();
    }

    @Override
    public void onResponseError(String message) {
        dismissProgressDialog();
        showSnackBar(mBinding, message);
    }

}
