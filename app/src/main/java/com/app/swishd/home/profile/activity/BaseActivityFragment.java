package com.app.swishd.home.profile.activity;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgActivityBinding;
import com.app.swishd.home.profile.ProfileContainerFragment;
import com.app.swishd.home.profile.activity.contact.ContactSendrFragment;
import com.app.swishd.home.profile.activity.contact.ContactSwishrFragment;
import com.app.swishd.home.profile.model.ActivityModel;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ValidFragment")
public class BaseActivityFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private FrgActivityBinding mBinding;
    private String jobID;
    private ActivityAdapter adapter;

    public BaseActivityFragment(String jobID) {
        this.jobID = jobID;
    }

    @Override
    public int getLayout() {
        return R.layout.frg_activity;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgActivityBinding) binding;
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
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new ActivityAdapter(getContext());
        mBinding.rvActivity.setAdapter(adapter);
        mBinding.btnContact.setText(BaseActivityFragment.this instanceof SwishrActivityFragment ? "CONTACT SENDR" : "CONTACT SWISHR");
        mBinding.btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseActivityFragment.this instanceof SwishrActivityFragment)
                    ((ProfileContainerFragment) getParentFragment()).addFragment(new ContactSwishrFragment(jobID), true);
                else
                    ((ProfileContainerFragment) getParentFragment()).addFragment(new ContactSendrFragment(jobID), true);
            }
        });
        loadData();
    }

    public void loadData() {
        showProgressDialog();
        getApiTask().jobActivity(jobID, callbackActivity);
    }

    private Callback<ActivityModel> callbackActivity = new Callback<ActivityModel>() {
        @Override
        public void onResponse(Call<ActivityModel> call, Response<ActivityModel> response) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                if (response.body().getActivities() != null && response.body().getActivities().size() > 0) {
                    adapter.setData(response.body().getActivities());
                    mBinding.tvError.setVisibility(View.GONE);
                    mBinding.swipeRefreshLayout.setVisibility(View.VISIBLE);
                } else {
                    mBinding.tvError.setVisibility(View.VISIBLE);
                    mBinding.swipeRefreshLayout.setVisibility(View.GONE);
                }
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                showSnackBar(error.getError());
                if (adapter.getItemCount() == 0) {
                    mBinding.tvError.setVisibility(View.VISIBLE);
                    mBinding.swipeRefreshLayout.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onFailure(Call<ActivityModel> call, Throwable t) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            showSnackBar(t.getMessage());
            if (adapter.getItemCount() == 0) {
                mBinding.tvError.setVisibility(View.VISIBLE);
                mBinding.swipeRefreshLayout.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onRefresh() {
        mBinding.swipeRefreshLayout.setRefreshing(false);
        loadData();
    }
}