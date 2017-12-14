
package com.app.swishd.home.profile.wallet.model;

import com.app.swishd.utility.DateUtil;
import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("dCreatedDate")
    private String mDCreatedDate;
    @SerializedName("sAmount")
    private Double mSAmount;
    @SerializedName("sDescription")
    private String mSDescription;
    @SerializedName("sPaymentFlow")
    private String mSPaymentFlow;
    @SerializedName("sPaymentMethod")
    private String mSPaymentMethod;
    @SerializedName("sPaymentType")
    private String mSPaymentType;
    @SerializedName("sTotal")
    private Double mSTotal;
    @SerializedName("user")
    private User mUser;
    @SerializedName("_id")
    private String m_id;

    public String getDCreatedDate() {
        return mDCreatedDate;
    }

    public void setDCreatedDate(String dCreatedDate) {
        mDCreatedDate = dCreatedDate;
    }

    public double getSAmount() {
        return mSAmount;
    }

    public void setSAmount(double sAmount) {
        mSAmount = sAmount;
    }

    public String getSDescription() {
        return mSDescription;
    }

    public void setSDescription(String sDescription) {
        mSDescription = sDescription;
    }

    public String getSPaymentFlow() {
        return mSPaymentFlow;
    }

    public void setSPaymentFlow(String sPaymentFlow) {
        mSPaymentFlow = sPaymentFlow;
    }

    public String getSPaymentMethod() {
        return mSPaymentMethod;
    }

    public void setSPaymentMethod(String sPaymentMethod) {
        mSPaymentMethod = sPaymentMethod;
    }

    public String getSPaymentType() {
        return mSPaymentType;
    }

    public void setSPaymentType(String sPaymentType) {
        mSPaymentType = sPaymentType;
    }

    public double getSTotal() {
        return mSTotal;
    }

    public void setSTotal(double sTotal) {
        mSTotal = sTotal;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }


    //.......... User defined functions ..........//
    public String getPaymentDate() {
        return DateUtil.getFormatedDate(mDCreatedDate,DateUtil.SERVER_DATE,"EEE dd MMM yyyy - HH:mm");
    }

    public String getPaymentAmount() {
        return (mSAmount < 0f ? "-" : "+") + " £" + mSAmount;
    }
}
