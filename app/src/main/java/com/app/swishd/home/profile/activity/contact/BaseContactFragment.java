package com.app.swishd.home.profile.activity.contact;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgContactBinding;
import com.app.swishd.home.profile.ProfileContainerFragment;
import com.app.swishd.home.profile.activity.BaseActivityFragment;
import com.app.swishd.home.profile.activity.SenderActivityFragment;
import com.app.swishd.home.profile.activity.SwishrActivityFragment;
import com.app.swishd.home.profile.activity.contact.model.ContactModel;
import com.app.swishd.home.profile.activity.contact.model.Message;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ValidFragment")
public class BaseContactFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ContactAdapter.MessageClickListener {
    private FrgContactBinding mBinding;
    private String jobID;
    private ContactAdapter adapter;

    public BaseContactFragment(String jobID) {
        this.jobID = jobID;
    }

    @Override
    public int getLayout() {
        return R.layout.frg_contact;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgContactBinding) binding;
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
        adapter = new ContactAdapter(getContext(), this);
        mBinding.rvContact.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        showProgressDialog();
        if (this instanceof ContactSwishrFragment)
            getApiTask().getSwishrMessages(callbackMessages);
        else
            getApiTask().getSenderMessages(callbackMessages);
    }

    private Callback<ContactModel> callbackMessages = new Callback<ContactModel>() {
        @Override
        public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                adapter.setData(response.body().getMessages());
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                showSnackBar(error.getError());
            }
        }

        @Override
        public void onFailure(Call<ContactModel> call, Throwable t) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            showSnackBar(t.getMessage());
        }
    };

    @Override
    public void onRefresh() {
        mBinding.swipeRefreshLayout.setRefreshing(false);
        loadData();
    }

    @Override
    public void onMessageClick(Message message, int position) {
        if (position == adapter.getItemCount() - 1) {
            ((ProfileContainerFragment) getParentFragment()).addFragment(new DraftMessageFragment(jobID), true);
        } else {
            showProgressDialog();
            getApiTask().sendPredefinedMessage(jobID, message.get_id(), callbackSendMessage);
        }
    }

    private Callback<Object> callbackSendMessage = new Callback<Object>() {
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