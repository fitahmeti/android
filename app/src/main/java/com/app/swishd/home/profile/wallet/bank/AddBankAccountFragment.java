package com.app.swishd.home.profile.wallet.bank;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgAddBankDetailBinding;
import com.app.swishd.home.profile.wallet.WalletFragment;
import com.app.swishd.home.profile.wallet.model.BankAccount;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddBankAccountFragment extends BaseFragment implements View.OnClickListener {
    private FrgAddBankDetailBinding mBinding;

    @Override
    public int getLayout() {
        return R.layout.frg_add_bank_detail;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgAddBankDetailBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragment().getChildFragmentManager().popBackStack();
            }
        });
        mBinding.edtCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d("Tag", "Action Done");
                    hideKeyboard();
                }
                return false;
            }
        });
        mBinding.btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isValid()) {
            hideKeyboard();

            final BankAccount bank = new BankAccount();
            bank.setSAccountName(mBinding.edtName.getText().toString().trim());
            bank.setSAccountNumber(mBinding.edtNumber.getText().toString().trim());
            bank.setSSortCode(mBinding.edtCode.getText().toString().trim());

            ConfirmAccountDialog dialog = new ConfirmAccountDialog(getContext(), R.style.DialogTheme);
            dialog.setCancelable(false);
            dialog.setAccount(bank);
            dialog.setDialogListener(new ConfirmAccountDialog.DialogListener() {
                @Override
                public void onConfirm() {
                    showProgressDialog();
                    getApiTask().addBankAccount(bank.getSAccountName(), bank.getSAccountNumber(), bank.getSSortCode(), callbackAddAccount);
                }

                @Override
                public void onCancel() {
                }
            });
            dialog.show();
        }
    }

    private boolean isValid() {
        String name = mBinding.edtName.getText().toString().trim();
        if (name.isEmpty()) {
            showSnackBar("Please enter account holder name.");
            return false;
        }
        String number = mBinding.edtNumber.getText().toString().trim();
        if (number.isEmpty()) {
            showSnackBar("Please enter account number.");
            return false;
        }
        if (number.length() < 10) {
            showSnackBar("Please enter valid account number.");
            return false;
        }
        String code = mBinding.edtCode.getText().toString().trim();
        if (code.isEmpty()) {
            showSnackBar("Please enter sort code");
            return false;
        }
        if (code.length() < 6) {
            showSnackBar("Please enter sort code of minimum 6 characters.");
            return false;
        }
        return true;
    }

    private Callback<AddAccountModel> callbackAddAccount = new Callback<AddAccountModel>() {
        @Override
        public void onResponse(Call<AddAccountModel> call, Response<AddAccountModel> response) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                ((WalletFragment) getParentFragment().getChildFragmentManager().findFragmentByTag(WalletFragment.class.getSimpleName())).onBankAccountAdded(response.body().getBankAccount());
                getParentFragment().getChildFragmentManager().popBackStack();
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                showSnackBar(error.getError());
            }
        }

        @Override
        public void onFailure(Call<AddAccountModel> call, Throwable t) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            showSnackBar(t.getMessage());
        }
    };
}
