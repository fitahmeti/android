
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

public class PickupBy {

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

    public String getmSEmail() {
        return mSEmail;
    }

    public void setmSEmail(String mSEmail) {
        this.mSEmail = mSEmail;
    }

    public String getmSFirstName() {
        return mSFirstName;
    }

    public void setmSFirstName(String mSFirstName) {
        this.mSFirstName = mSFirstName;
    }

    public String getmSLastName() {
        return mSLastName;
    }

    public void setmSLastName(String mSLastName) {
        this.mSLastName = mSLastName;
    }

    public String getmSMobile() {
        return mSMobile;
    }

    public void setmSMobile(String mSMobile) {
        this.mSMobile = mSMobile;
    }

    public String getmSSwishrEmail() {
        return mSSwishrEmail;
    }

    public void setmSSwishrEmail(String mSSwishrEmail) {
        this.mSSwishrEmail = mSSwishrEmail;
    }

    public String getmSSwishrUserName() {
        return mSSwishrUserName;
    }

    public void setmSSwishrUserName(String mSSwishrUserName) {
        this.mSSwishrUserName = mSSwishrUserName;
    }

    public String getmSUserId() {
        return mSUserId;
    }

    public void setmSUserId(String mSUserId) {
        this.mSUserId = mSUserId;
    }
}
