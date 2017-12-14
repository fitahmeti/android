
package com.app.swishd.home.profile.wallet.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankAccountModel {

    @SerializedName("data")
    private List<BankAccount> mBankAccount;
    @SerializedName("message")
    private String mMessage;

    public List<BankAccount> getBankAccount() {
        return mBankAccount;
    }

    public void setBankAccount(List<BankAccount> bankAccount) {
        mBankAccount = bankAccount;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
