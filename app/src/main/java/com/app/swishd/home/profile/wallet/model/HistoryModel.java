
package com.app.swishd.home.profile.wallet.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("wallet")
    private List<Transaction> transaction;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }
}
