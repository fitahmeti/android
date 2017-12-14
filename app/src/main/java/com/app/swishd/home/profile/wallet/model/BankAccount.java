
package com.app.swishd.home.profile.wallet.model;

import com.google.gson.annotations.SerializedName;

public class BankAccount {

    @SerializedName("sAccountName")
    private String mSAccountName;
    @SerializedName("sAccountNumber")
    private String mSAccountNumber;
    @SerializedName("sSortCode")
    private String mSSortCode;
    @SerializedName("_id")
    private String m_id;

    public String getSAccountName() {
        return mSAccountName;
    }

    public void setSAccountName(String sAccountName) {
        mSAccountName = sAccountName;
    }

    public String getSAccountNumber() {
        return mSAccountNumber;
    }

    public void setSAccountNumber(String sAccountNumber) {
        mSAccountNumber = sAccountNumber;
    }

    public String getSSortCode() {
        return mSSortCode;
    }

    public void setSSortCode(String sSortCode) {
        mSSortCode = sSortCode;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }


    //.............User defined functions................//
    public String getAccountNumber() {
        return mSAccountNumber;
    }
}
