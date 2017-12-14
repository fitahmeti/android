package com.app.swishd.home.notification;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgNotificationBinding;
import com.app.swishd.databinding.RowNotificationBinding;
import com.app.swishd.home.notification.model.Notification;
import com.app.swishd.home.notification.model.NotificationModel;
import com.app.swishd.home.profile.jobs.ActiveJobSwisHrFragment;
import com.app.swishd.home.search.fragment.SwissJobDetailsFragment;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;
import com.app.swishd.retrofit.call.Api;
import com.app.swishd.utility.DateUtil;
import com.app.swishd.utility.ImageUtil;
import com.app.swishd.utility.Utility;
import com.app.swishd.widget.LoadMoreHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationFragment extends BaseFragment implements LoadMoreHelper.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private FrgNotificationBinding mBinding;
    private LoadMoreHelper loadMoreHelper;
    private NotificationAdapter adapter;
    private ArrayList<Notification> notificationList = new ArrayList<>();
    private int startLoadingPos;
    private Call<NotificationModel> callNotification;

    @Override
    public int getLayout() {
        return R.layout.frg_notification;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgNotificationBinding) binding;
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
        adapter = new NotificationAdapter(getContext());
        mBinding.rvNotification.setAdapter(adapter);
        loadMoreHelper = new LoadMoreHelper.Builder(mBinding.rvNotification).pageSize(20).listener(this).build();
        loadData(0);
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void loadData(int start) {
        mBinding.progressBar.setVisibility(View.VISIBLE);
        startLoadingPos = start;
        callNotification = getApiTask().getNotificationLog(start, loadMoreHelper.getPageSize(), callbackNotification);
    }

    private Callback<NotificationModel> callbackNotification = new Callback<NotificationModel>() {
        @Override
        public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
            callNotification = null;
            if (call.isCanceled())
                return;
            mBinding.progressBar.setVisibility(View.GONE);
            if (response.isSuccessful()) {
                loadMoreHelper.setItemCount(response.body().getTotalUnread());
                if (startLoadingPos == 0) {
                    notificationList.clear();
                    adapter.notifyDataSetChanged();
                }
                if (response.body().getNotifications() != null) {
                    notificationList.addAll(response.body().getNotifications());
                    adapter.notifyItemRangeInserted(adapter.getItemCount() - response.body().getNotifications().size(), response.body().getNotifications().size());
                }
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                showSnackBar(error.getError());
            }
        }

        @Override
        public void onFailure(Call<NotificationModel> call, Throwable t) {
            callNotification = null;
            if (call.isCanceled())
                return;
            mBinding.progressBar.setVisibility(View.GONE);
            showSnackBar(t.getMessage());
        }
    };

    @Override
    public void onLoadMore(int lastItemPosition) {
        if (callNotification == null)
            loadData(adapter.getItemCount());
    }

    @Override
    public void onRefresh() {
        mBinding.swipeRefreshLayout.setRefreshing(false);
        if (callNotification == null)
            loadData(0);
    }

    private void onNotificationClick(Notification notification) {
        if (!"wallet".equalsIgnoreCase(notification.getSType()) && notification.getSJobId() != null && !notification.getSJobId().isEmpty()) {
            if ("sendr_accept".equalsIgnoreCase(notification.getSType()) || "reject_sendr".equalsIgnoreCase(notification.getSType())|| "swishr_offer".equalsIgnoreCase(notification.getSType())|| "new_job".equalsIgnoreCase(notification.getSType()))
                openJobFragment(ActiveJobSwisHrFragment.getInstance(notification.getSJobId(), false, false));
//            else if ("new_job".equalsIgnoreCase(notification.getSType())) {
//                SwissJobDetailsFragment fragment = SwissJobDetailsFragment.getInstance(notification.getSJobId());
//                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                transaction.addToBackStack(fragment.getClass().getSimpleName());
//                transaction.add(R.id.container, fragment, fragment.getClass().getSimpleName());
//                transaction.commit();
//            }
        }
    }

    private void openJobFragment(BaseFragment fragment) {
        ((BaseContainerFragment) getParentFragment()).addFragment(
                fragment, true);
    }

    private class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
        private final LayoutInflater inflater;

        public NotificationAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            RowNotificationBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.row_notification, viewGroup, false);
            return new ViewHolder(mBinding);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.mBinding.tvTitle.setText(notificationList.get(position).getSDescription());
            viewHolder.mBinding.tvTime.setText(Utility.getAgoStringFromTime(DateUtil.getMillis(notificationList.get(position).getDCreatedDate(), DateUtil.SERVER_DATE)));
            if (notificationList.get(position).getUser() != null && notificationList.get(position).getUser().getProfileImage() != null && !notificationList.get(position).getUser().getProfileImage().isEmpty())
                ImageUtil.load(Api.BASE_URL_IMAGE + notificationList.get(position).getUser().getProfileImage(), viewHolder.mBinding.imgProfile, R.drawable.ic_person);
            else
                viewHolder.mBinding.imgProfile.setImageResource(R.drawable.ic_person);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNotificationClick(notificationList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return notificationList.size();
        }

        protected class ViewHolder extends RecyclerView.ViewHolder {
            private RowNotificationBinding mBinding;

            public ViewHolder(RowNotificationBinding mBinding) {
                super(mBinding.getRoot());
                this.mBinding = mBinding;
            }
        }
    }
}
