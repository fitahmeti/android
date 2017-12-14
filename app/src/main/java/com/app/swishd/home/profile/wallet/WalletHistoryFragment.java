package com.app.swishd.home.profile.wallet;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.FrgWalletHistoryBinding;
import com.app.swishd.databinding.RowTransactionHistoryBinding;
import com.app.swishd.home.profile.wallet.model.HistoryModel;
import com.app.swishd.home.profile.wallet.model.Transaction;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;
import com.app.swishd.widget.LoadMoreHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletHistoryFragment extends BaseWalletPagerFragment implements LoadMoreHelper.LoadMoreListener {
    private FrgWalletHistoryBinding mBinding;
    private LoadMoreHelper loadMoreHelper;
    private ArrayList<Transaction> historyList = new ArrayList<>();
    private HistoryAdapter adapter;
    private int callListStart;

    @Override
    public int getLayout() {
        return R.layout.frg_wallet_history;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgWalletHistoryBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.rvHistory.setNestedScrollingEnabled(false);
        adapter = new HistoryAdapter(getActivity());
        mBinding.rvHistory.setAdapter(adapter);
        loadMoreHelper = new LoadMoreHelper.Builder(mBinding.rvHistory).pageSize(20).listener(this).build();
        loadData(0);
    }

    private void loadData(int start) {
        callListStart = start;
        mBinding.progressBar.setVisibility(View.VISIBLE);
        getApiTask().getTransactionHistory(start, loadMoreHelper.getPageSize(), callbackHistory);
    }

    private Callback<HistoryModel> callbackHistory = new Callback<HistoryModel>() {
        @Override
        public void onResponse(Call<HistoryModel> call, Response<HistoryModel> response) {
            mBinding.progressBar.setVisibility(View.GONE);
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                if (callListStart == 0) {
                    historyList.clear();
                    adapter.notifyDataSetChanged();
                }
                historyList.addAll(response.body().getTransaction());
                adapter.notifyItemRangeInserted(adapter.getItemCount() - response.body().getTransaction().size(), response.body().getTransaction().size());
                if (historyList.size() != 0)
                    mBinding.tvError.setVisibility(View.GONE);
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                showSnackBar(error.getError());
            }
        }

        @Override
        public void onFailure(Call<HistoryModel> call, Throwable t) {
            mBinding.progressBar.setVisibility(View.GONE);
            if (call.isCanceled())
                return;
            showSnackBar(t.getMessage());
        }
    };

    @Override
    public void onLoadMore(int lastItemPosition) {
        loadData(lastItemPosition);
    }

    @Override
    public void onRefresh() {
        loadData(0);
    }

    private class HistoryAdapter extends RecyclerView.Adapter<ViewHolder> {

        private LayoutInflater inflater;

        private HistoryAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RowTransactionHistoryBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.row_transaction_history, parent, false);
            return new ViewHolder(mBinding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mBinding.setTransaction(historyList.get(position));
//            if (historyList.get(position).getUser().getProfileImage() != null && !historyList.get(position).getUser().getProfileImage().isEmpty())
//                ImageUtil.load(Api.BASE_URL_IMAGE + historyList.get(position).getUser().getProfileImage(), holder.mBinding.imageView, R.drawable.ic_person);
        }

        @Override
        public int getItemCount() {
            return historyList.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private final RowTransactionHistoryBinding mBinding;

        public ViewHolder(RowTransactionHistoryBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}