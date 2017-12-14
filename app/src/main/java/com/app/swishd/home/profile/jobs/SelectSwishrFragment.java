package com.app.swishd.home.profile.jobs;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgSelectSwishrBinding;
import com.app.swishd.home.profile.ProfileContainerFragment;
import com.app.swishd.home.profile.adapter.OfferListAdapter;
import com.app.swishd.home.profile.dialog.AcceptOfferDailog;
import com.app.swishd.home.profile.model.RecieptModel;
import com.app.swishd.home.profile.model.offer.DataItem;
import com.app.swishd.home.profile.model.offer.ResponseOfferList;
import com.app.swishd.home.search.model.JobDetails.Data;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.retrofit.call.Api;
import com.app.swishd.utility.ImageUtil;

import java.util.List;

import io.reactivex.disposables.Disposable;

import static com.app.swishd.utility.Const.SORT_BY.date;

public class SelectSwishrFragment extends BaseFragment
        implements OnResponseCallback<ResponseOfferList>, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, AcceptOfferDailog.AcceptOfferCallback {

    private FrgSelectSwishrBinding mBinding;
    private Data mJobDetails;
    private Disposable mCall;
    private OfferListAdapter mAdapter;
    private List<DataItem> mItem;
    private int position = -1;

    public static SelectSwishrFragment getInstance(Data jobDetails) {
        SelectSwishrFragment mFragment = new SelectSwishrFragment();
        mFragment.mJobDetails = jobDetails;
        return mFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frg_select_swishr;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgSelectSwishrBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        doGetOfferList();
        mBinding.setItem(mJobDetails);

        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragment().getChildFragmentManager().popBackStack();
            }
        });

        ImageUtil.load(Api.BASE_URL_IMAGE + mJobDetails.getJobSize().getSOriginalSizePicture()
                , mBinding.rowJobListItemImage);

        mBinding.frgSelectSwishrRefresh.setOnRefreshListener(this);
    }

    private void doGetOfferList() {
        mCall = getApiTask().doGetOfferList(
                getContext(), mJobDetails.getId(),
                date.toString(), this
        );
    }

    @Override
    public void onResponseReceived(ResponseOfferList model, int requestCode) {
        setAdapter(model);
    }

    private void setAdapter(ResponseOfferList model) {
        mBinding.frgSelectSwishrProgressBar.setVisibility(View.GONE);
        if (mAdapter == null) {
            mItem = model.getData();
            mAdapter = new OfferListAdapter(getContext(), mItem, this);
            mBinding.frgSelectSwishrList.setAdapter(mAdapter);
        } else {
            mItem = model.getData();
            mAdapter.notifyDataSetChanged();
        }
        mBinding.frgSelectSwishrList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponseError(String message) {
        showSnackBar(mBinding, message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCall != null)
            mCall.dispose();
        mCall = null;
        mBinding = null;
    }

    @Override
    public void onRefresh() {
        mBinding.frgSelectSwishrRefresh.setRefreshing(false);
        mBinding.frgSelectSwishrList.setVisibility(View.GONE);
        mBinding.frgSelectSwishrProgressBar.setVisibility(View.VISIBLE);
        doGetOfferList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.row_select_accept_swishr:
                position = (Integer) v.getTag();
                DataItem item = mItem.get(position);
                AcceptOfferDailog.getInstance(
                        item.getUsername(),
                        item.getProfileImage(),
                        this).show(getChildFragmentManager(), "Accept Offer");
//                doAcceptJob();
                break;
        }
    }

    private void doAcceptJob() {
        getApiTask().doAcceptJob(getContext(), mJobDetails.getId(), null, this);
    }

    @Override
    public void onAcceptOffer() {
        if (position == -1)
            return;

        RecieptModel model = new RecieptModel();
        model.setFrom(mJobDetails.getSPickAddress());
        model.setCollectFrom(mJobDetails.getFormattedPickDateReceipt());
        model.setTo(mJobDetails.getSDropAddress());
        model.setDeliveredBy(mJobDetails.getFormattedDropDateReceipt());
        ((ProfileContainerFragment) getParentFragment()).addFragment(
                RecieptSwishrFragment.getInstance(model, mJobDetails.getId(),
                        mItem.get(position).getId()), true
        );
    }
}
