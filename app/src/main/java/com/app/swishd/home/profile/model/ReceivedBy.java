
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

public class ReceivedBy {

    @SerializedName("sEmail")
    private String mSEmail;
    @SerializedName("sFirstName")
    private String mSFirstName;
    @SerializedName("sLastName")
    private String mSLastName;
    @SerializedName("sMobile")
    private String mSMobile;
    @SerializedName("sSwishrEmail")
    private String mSSwishrEmail;
    @SerializedName("sSwishrUserName")
    private String mSSwishrUserName;
    @SerializedName("sUserId")
    private String mSUserId;

    public String getSEmail() {
        return mSEmail;
    }

    public void setSEmail(String sEmail) {
        mSEmail = sEmail;
    }

    public String getSFirstName() {
        return mSFirstName;
    }

    public void setSFirstName(String sFirstName) {
        mSFirstName = sFirstName;
    }

    public String getSLastName() {
        return mSLastName;
    }

    public void setSLastName(String sLastName) {
        mSLastName = sLastName;
    }

    public String getSMobile() {
        return mSMobile;
    }

    public void setSMobile(String sMobile) {
        mSMobile = sMobile;
    }

    public String getSSwishrEmail() {
        return mSSwishrEmail;
    }

    public void setSSwishrEmail(String sSwishrEmail) {
        mSSwishrEmail = sSwishrEmail;
    }

    public String getSSwishrUserName() {
        return mSSwishrUserName;
    }

    public void setSSwishrUserName(String sSwishrUserName) {
        mSSwishrUserName = sSwishrUserName;
    }

    public String getSUserId() {
        return mSUserId;
    }

    public void setSUserId(String sUserId) {
        mSUserId = sUserId;
    }

}
