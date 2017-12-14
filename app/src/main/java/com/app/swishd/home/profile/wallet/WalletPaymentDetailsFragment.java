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
import com.app.swishd.baseutil.BaseContainerFragment;
import com.app.swishd.databinding.FrgWalletPaymentDetailsBinding;
import com.app.swishd.databinding.RowBankAccountBinding;
import com.app.swishd.home.profile.wallet.bank.AddBankAccountFragment;
import com.app.swishd.home.profile.wallet.card.AddCardFragment;
import com.app.swishd.home.profile.wallet.model.BankAccount;
import com.app.swishd.home.profile.wallet.model.BankAccountModel;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletPaymentDetailsFragment extends BaseWalletPagerFragment {
    private FrgWalletPaymentDetailsBinding mBinding;
    private List<BankAccount> bankList = new ArrayList<>();
    private BankAdapter bankAdapter;

    @Override
    public int getLayout() {
        return R.layout.frg_wallet_payment_details;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgWalletPaymentDetailsBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBinding.rvBankAccount.setNestedScrollingEnabled(false);
        mBinding.rvCards.setNestedScrollingEnabled(false);

        bankAdapter = new BankAdapter(getActivity());
        mBinding.rvBankAccount.setAdapter(bankAdapter);
        loadData();

        mBinding.btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCardFragment fragment = new AddCardFragment();
                ((BaseContainerFragment) getParentFragment().getParentFragment()).addFragment(fragment, true);
            }
        });
        mBinding.btnAddBankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBankAccountFragment fragment = new AddBankAccountFragment();
                ((BaseContainerFragment) getParentFragment().getParentFragment()).addFragment(fragment, true);
            }
        });
    }

    private void loadData() {
        mBinding.progressBar.setVisibility(View.VISIBLE);
        getApiTask().getBankAccounts(callbackBankAccount);
    }

    private Callback<BankAccountModel> callbackBankAccount = new Callback<BankAccountModel>() {
        @Override
        public void onResponse(Call<BankAccountModel> call, Response<BankAccountModel> response) {
            mBinding.progressBar.setVisibility(View.GONE);
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                if (response.body().getBankAccount() != null && response.body().getBankAccount().size() > 0) {
                    bankList.clear();
                    bankList.addAll(response.body().getBankAccount());
                    bankAdapter.notifyDataSetChanged();
                }
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                showSnackBar(error.getError());
            }
        }

        @Override
        public void onFailure(Call<BankAccountModel> call, Throwable t) {
            mBinding.progressBar.setVisibility(View.GONE);
            if (call.isCanceled())
                return;
            showSnackBar(t.getMessage());
        }
    };

    @Override
    public void onRefresh() {
        loadData();
    }

    public void onBankAccountAdded(BankAccount bankAccount) {
        bankList.add(bankAccount);
        bankAdapter.notifyItemInserted(bankAdapter.getItemCount() - 1);
    }

    private class BankAdapter extends RecyclerView.Adapter<ViewHolder> {

        private LayoutInflater inflater;

        private BankAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RowBankAccountBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.row_bank_account, parent, false);
            return new ViewHolder(mBinding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (bankList.get(position) != null)
                holder.mBinding.setBankDetail(bankList.get(position));
        }

        @Override
        public int getItemCount() {
            if (bankList == null)
                return 0;
            else
                return bankList.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private RowBankAccountBinding mBinding;

        public ViewHolder(RowBankAccountBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}
