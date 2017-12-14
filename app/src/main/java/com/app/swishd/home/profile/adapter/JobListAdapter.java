package com.app.swishd.home.profile.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.RowProfileJobBinding;
import com.app.swishd.home.profile.model.Job;
import com.app.swishd.utility.ImageUtil;
import com.app.swishd.widget.SendrHeaderView;

import java.util.List;


public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {

    private List<Job> jobList;
    private Context context;
    private final JobItemClickListener itemClickListener;
    private final LayoutInflater inflater;
    private boolean isSender;

    public JobListAdapter(Context context, JobItemClickListener itemClickListener, boolean isSender) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        inflater = LayoutInflater.from(context);
        this.isSender = isSender;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowProfileJobBinding mBinding = DataBindingUtil.inflate(inflater,
                                                                R.layout.row_profile_job, parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (isSender) {
            if (position == 0) {
                if (holder.mBinding.llRoot.getChildCount() == 1) {
                    SendrHeaderView sendrHeaderView = new SendrHeaderView(context);
                    sendrHeaderView.setJob(jobList.get(position));
                    holder.mBinding.llRoot.addView(sendrHeaderView, 0);
                }
            } else {
                if (holder.mBinding.llRoot.getChildCount() != 1)
                    holder.mBinding.llRoot.removeViewAt(0);
                if (jobList.get(position).getOrder() != jobList.get(position - 1).getOrder()) {
                    SendrHeaderView sendrHeaderView = new SendrHeaderView(context);
                    sendrHeaderView.setJob(jobList.get(position));
                    holder.mBinding.llRoot.addView(sendrHeaderView, 0);
                }
            }
        }

        holder.mBinding.setJob(jobList.get(position));
        ImageUtil.load(jobList.get(position).getJobSize().getSizePicture(), holder.mBinding.imgSize);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onJobItemClick(jobList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (jobList == null) return 0;
        else return jobList.size();
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final RowProfileJobBinding mBinding;

        public ViewHolder(RowProfileJobBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }

    public interface JobItemClickListener {
        void onJobItemClick(Job job);
    }
}
