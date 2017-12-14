package com.app.swishd.home.profile.more;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.app.swishd.R;
import com.app.swishd.databinding.RowNotificationSettingsBinding;
import com.app.swishd.databinding.RowNotificationSettingsHeaderBinding;
import com.app.swishd.home.profile.model.NotificationSetting;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationSettingsAdapter extends RecyclerView.Adapter implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;
    private Context context;
    private List<NotificationSetting> items = new ArrayList<>();
    private OnSettingsToggleListener settingsToggleListener;

    public NotificationSettingsAdapter(Context context, OnSettingsToggleListener settingsToggleListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.settingsToggleListener = settingsToggleListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowNotificationSettingsBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.row_notification_settings, parent, false);
        return new ItemViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        holder.mBinding.tvTitle.setText(items.get(position).getSettingName());
        holder.mBinding.btnSwitch.setOnCheckedChangeListener(null);
        holder.mBinding.btnSwitch.setChecked(items.get(position).getStatus());
         holder.mBinding.btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (settingsToggleListener != null) {
                    settingsToggleListener.onSettingToggled(position);
                }
            }
        });
    }

    @Override
    public long getHeaderId(int i) {
        return i / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        RowNotificationSettingsHeaderBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.row_notification_settings_header, viewGroup, false);
        return new HeaderViewHolder(mBinding);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
        holder.mBinding.tvHeader.setText(items.get(i).getType());
    }

    public void setItems(List<NotificationSetting> settings) {
        this.items = settings;
        notifyDataSetChanged();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final RowNotificationSettingsHeaderBinding mBinding;

        public HeaderViewHolder(RowNotificationSettingsHeaderBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final RowNotificationSettingsBinding mBinding;

        public ItemViewHolder(RowNotificationSettingsBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;
        return items.size();
    }

    public interface OnSettingsToggleListener {
        void onSettingToggled(int position);
    }
}