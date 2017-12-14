package com.app.swishd.home.profile.jobs;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgProfileJobBinding;
import com.app.swishd.home.profile.ProfileFragment;
import com.app.swishd.home.profile.adapter.JobListAdapter;
import com.app.swishd.home.profile.model.Job;
import com.app.swishd.home.profile.model.JobListModel;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;
import com.app.swishd.utility.Const;
import com.app.swishd.widget.LoadMoreHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseJobFragment extends BaseFragment
        implements JobListAdapter.JobItemClickListener, LoadMoreHelper.LoadMoreListener {

    private JobListAdapter jobsAdapter;
    private LoadMoreHelper loadMoreHelper;
    private Call<JobListModel> callJobList;
    private JobListModel jobListModel;
    private FrgProfileJobBinding mBinding;
    private int callListStart;
    private Callback<JobListModel> callbackJobList = new Callback<JobListModel>() {
        @Override
        public void onResponse(Call<JobListModel> call, Response<JobListModel> response) {
            mBinding.progressBar.setVisibility(View.GONE);
            callJobList = null;
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                int jobCount = response.body().getTotalCount();
                if (BaseJobFragment.this instanceof SenderJobListFragment)
                    ProfileFragment.getInstance().setSenderCount(jobCount);
                else
                    ProfileFragment.getInstance().setSwishrCount(jobCount);
                loadMoreHelper.setItemCount(jobCount);
                if (callListStart == 0) {
                    jobListModel = response.body();
                    jobsAdapter.setJobList(jobListModel.getJobs());
                } else {
                    jobListModel.getJobs().addAll(response.body().getJobs());
                    jobsAdapter.notifyItemRangeInserted(jobsAdapter.getItemCount(), response.body().getJobs().size());
                }
                if (jobListModel == null || jobListModel.getJobs() == null || jobListModel.getJobs().size() == 0) {
                    mBinding.emptyView.setVisibility(View.VISIBLE);
                    //mBinding.btnViewHistory.setVisibility(View.GONE);
                } else {
                    mBinding.emptyView.setVisibility(View.GONE);
                    //mBinding.btnViewHistory.setVisibility(View.VISIBLE);
                }
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                showErrorView(error.getError());
            }
        }

        @Override
        public void onFailure(Call<JobListModel> call, Throwable t) {
            mBinding.progressBar.setVisibility(View.GONE);
            callJobList = null;
            if (call.isCanceled())
                return;
            showErrorView(t.getMessage());
        }
    };

    @Override
    public int getLayout() {
        return R.layout.frg_profile_job;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgProfileJobBinding) binding;
        jobsAdapter = new JobListAdapter(getContext(), this, this instanceof SenderJobListFragment);
        mBinding.rvJob.setAdapter(jobsAdapter);
        loadMoreHelper = new LoadMoreHelper.Builder(mBinding.rvJob).listener(this).pageSize(10).build();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (this instanceof SenderJobListFragment) {
            mBinding.tvEmptyMessage.setText("You have no items being sent currently.");
            mBinding.btnEmpty.setVisibility(View.GONE);
        } else {
            mBinding.tvEmptyMessage.setText("You are currently not Swishing any items");
            mBinding.btnEmpty.setVisibility(View.VISIBLE);
        }
        reloadJobList();
    }

    public void reloadJobListIfNeeded() {
        if (jobListModel != null)
            return;
        reloadJobList();
    }

    public void reloadJobList() {
        loadJobList(0, 10);
    }

    private void loadJobList(int start, int limit) {
        if (callJobList != null)
            return;
        callListStart = start;
        mBinding.progressBar.setVisibility(View.VISIBLE);
        hideErrorView();
        if (this instanceof SenderJobListFragment)
            callJobList = getApiTask().getMySenders(start, limit, callbackJobList);
        else
            callJobList = getApiTask().getMySwishers(start, limit, callbackJobList);
    }

    private void showErrorView(String message) {
        showSnackBar(message);
//        if (jobListModel == null) {
//            mBinding.tvError.setText(message);
//            mBinding.tvError.setVisibility(View.VISIBLE);
//        } else {
//            showSnackBar(message);
//        }
    }

    private void hideErrorView() {
        mBinding.tvError.setVisibility(View.GONE);
    }

    @Override
    public void onJobItemClick(Job job) {
        if (job.getEJobStatus().equals(Const.JOB_STATUS.active.toString()))
            openFragment(ActiveJobSwisHrFragment.getInstance(job.get_id(), this instanceof SwisherJobListFragment, true));
        else
            openFragment(ActiveJobSwisHrFragment.getInstance(job.get_id(), this instanceof SwisherJobListFragment, false));
    }

    private void openFragment(BaseFragment fragment) {
        ((BaseContainerFragment) getParentFragment().getParentFragment()).addFragment(
                fragment, true);
    }

    @Override
    public void onLoadMore(int lastItemPosition) {
        loadJobList(lastItemPosition, loadMoreHelper.getPageSize());
    }

}