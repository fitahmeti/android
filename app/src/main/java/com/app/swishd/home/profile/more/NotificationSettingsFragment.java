package com.app.swishd.home.profile.more;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgNotificationSettingsBinding;
import com.app.swishd.home.profile.model.GetNotificationSettingsModel;
import com.app.swishd.home.profile.model.NotificationSetting;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationSettingsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, NotificationSettingsAdapter.OnSettingsToggleListener {
    private FrgNotificationSettingsBinding mBinding;
    private NotificationSettingsAdapter adapter;
    private List<NotificationSetting> settingsList;
    private int positionToUpdate;
    private Call<Object> callUpdateSetting;
    private Call<GetNotificationSettingsModel> callRefreshSettings;

    @Override
    public int getLayout() {
        return R.layout.frg_notification_settings;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgNotificationSettingsBinding) binding;
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

        //https://github.com/timehop/sticky-headers-recyclerview/blob/master/sample/src/main/java/com/timehop/stickyheadersrecyclerview/sample/MainActivity.java
        adapter = new NotificationSettingsAdapter(getContext(), this);
        mBinding.rvSettings.setAdapter(adapter);

        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        mBinding.rvSettings.addItemDecoration(headersDecor);

//        mBinding.rvSettings.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//            }
//        }));
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
                mBinding.rvSettings.invalidateItemDecorations();
            }
        });

        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        loadSettings();
    }

    private void loadSettings() {
        showProgressDialog();
        getApiTask().getNotificationSettings(callbackGetSettings);
    }

    private Callback<GetNotificationSettingsModel> callbackGetSettings = new Callback<GetNotificationSettingsModel>() {
        @Override
        public void onResponse(Call<GetNotificationSettingsModel> call, Response<GetNotificationSettingsModel> response) {
            callRefreshSettings = null;
            dismissProgressDialog();
            mBinding.swipeRefreshLayout.setRefreshing(false);
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                settingsList = response.body().getNotificationSetting();
                adapter.setItems(settingsList);
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                showSnackBar(error.getError());
            }
        }

        @Override
        public void onFailure(Call<GetNotificationSettingsModel> call, Throwable t) {
            callRefreshSettings = null;
            dismissProgressDialog();
            mBinding.swipeRefreshLayout.setRefreshing(false);
            if (call.isCanceled())
                return;
            showSnackBar(t.getMessage());
        }
    };

    Callback<Object> callbackUpdateSetting = new Callback<Object>() {
        @Override
        public void onResponse(Call<Object> call, Response<Object> response) {
            callUpdateSetting = null;
            dismissProgressDialog();
            if (call.isCanceled()) {
                adapter.notifyDataSetChanged();
                return;
            }
            if (!response.isSuccessful()) {
                ResponseError error = ErrorUtils.parseError(response);
                showSnackBar(error.getError());
            } else {
                settingsList.get(positionToUpdate).setStatus(!settingsList.get(positionToUpdate).getStatus());
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<Object> call, Throwable t) {
            callUpdateSetting = null;
            dismissProgressDialog();
            adapter.notifyDataSetChanged();
            if (call.isCanceled())
                return;
            showSnackBar(t.getMessage());
        }
    };

    @Override
    public void onRefresh() {
        if (callRefreshSettings != null || callUpdateSetting != null) {
            mBinding.swipeRefreshLayout.setRefreshing(false);
            return;
        }
        callRefreshSettings = getApiTask().getNotificationSettings(callbackGetSettings);
    }

    @Override
    public void onSettingToggled(int position) {
        if (mBinding.swipeRefreshLayout.isRefreshing() || callUpdateSetting != null) {
            adapter.notifyDataSetChanged();
            return;
        }
        showProgressDialog();
        positionToUpdate = position;
        callUpdateSetting = getApiTask().updateNotificationSetting(settingsList.get(position).getId(), !settingsList.get(position).getStatus(), callbackUpdateSetting);
    }
}