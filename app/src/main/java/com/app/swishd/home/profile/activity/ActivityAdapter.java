package com.app.swishd.home.profile.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.RowActivityBinding;
import com.app.swishd.home.profile.model.Activity;
import com.app.swishd.utility.DateUtil;
import com.app.swishd.utility.Utility;

import java.util.List;


public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Activity> activityList;

    public ActivityAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowActivityBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.row_activity, parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mBinding.tvMessage.setText(activityList.get(position).getSMessage());
        holder.mBinding.tvTime.setText(Utility.getAgoStringFromTime(DateUtil.getMillis(activityList.get(position).getDMessageDate(), DateUtil.SERVER_DATE)));
    }

    @Override
    public int getItemCount() {
        if (activityList == null)
            return 0;
        else
            return activityList.size();
    }

    public void setData(List<Activity> activityList) {
        this.activityList = activityList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowActivityBinding mBinding;

        public ViewHolder(RowActivityBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}
