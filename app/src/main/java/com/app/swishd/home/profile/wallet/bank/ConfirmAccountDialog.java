package com.app.swishd.home.profile.wallet.bank;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.databinding.DialogConfirmAddAccountBinding;
import com.app.swishd.home.profile.wallet.model.BankAccount;


public class ConfirmAccountDialog extends AlertDialog {
    private BankAccount bankAccount;
    private DialogConfirmAddAccountBinding mBinding;
    private DialogListener dialogListener;

    protected ConfirmAccountDialog(@NonNull Context context) {
        super(context);
        init();
    }

    protected ConfirmAccountDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected ConfirmAccountDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_confirm_add_account, null, false);
        setView(mBinding.getRoot());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.setBankDetail(bankAccount);
        mBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogListener != null)
                    dialogListener.onConfirm();
                dismiss();
            }
        });
        mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogListener != null)
                    dialogListener.onCancel();
                dismiss();
            }
        });
    }

    public void setAccount(BankAccount bank) {
        this.bankAccount = bank;
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public interface DialogListener {
        void onConfirm();

        void onCancel();
    }
}
