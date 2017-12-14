
package com.app.swishd.home.profile.wallet.bank;

import com.app.swishd.home.profile.wallet.model.BankAccount;
import com.google.gson.annotations.SerializedName;

public class AddAccountModel {

    @SerializedName("data")
    private BankAccount mBankAccount;
    @SerializedName("message")
    private String mMessage;

    public BankAccount getBankAccount() {
        return mBankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        mBankAccount = bankAccount;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
