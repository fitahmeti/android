package com.app.swishd.home.profile.activity.contact;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgDraftMessageBinding;
import com.app.swishd.home.profile.activity.BaseActivityFragment;
import com.app.swishd.home.profile.activity.SenderActivityFragment;
import com.app.swishd.home.profile.activity.SwishrActivityFragment;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ValidFragment")
public class DraftMessageFragment extends BaseFragment {

    private String jobId;

    private FrgDraftMessageBinding mBinding;

    public DraftMessageFragment(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public int getLayout() {
        return R.layout.frg_draft_message;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgDraftMessageBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragment().getChildFragmentManager().popBackStack();
            }
        });
        mBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mBinding.edtMessage.getText().toString().trim();
                if (message.isEmpty()) {
                    showSnackBar("Please enter message");
                    return;
                }
                showProgressDialog();
                getApiTask().sendMessage(jobId, message, callbackMessage);
            }
        });
    }

    private Callback<Object> callbackMessage = new Callback<Object>() {
        @Override
        public void onResponse(Call<Object> call, Response<Object> response) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                if ((getParentFragment()).getChildFragmentManager().findFragmentByTag(SwishrActivityFragment.class.getSimpleName()) != null) {
                    (getParentFragment()).getChildFragmentManager().popBackStack(SwishrActivityFragment.class.getSimpleName(), 0);
                    BaseActivityFragment frgActivity = (SwishrActivityFragment) (getParentFragment()).getChildFragmentManager().findFragmentByTag(SwishrActivityFragment.class.getSimpleName());
                    frgActivity.loadData();
                } else {
                    (getParentFragment()).getChildFragmentManager().popBackStack(SenderActivityFragment.class.getSimpleName(), 0);
                    BaseActivityFragment frgActivity = (SenderActivityFragment) (getParentFragment()).getChildFragmentManager().findFragmentByTag(SenderActivityFragment.class.getSimpleName());
                    frgActivity.loadData();
                }
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