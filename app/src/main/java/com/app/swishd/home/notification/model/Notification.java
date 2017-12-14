
package com.app.swishd.home.notification.model;

import com.app.swishd.home.profile.model.UserProfile;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("dCreatedDate")
    private String mDCreatedDate;
    @SerializedName("sDescription")
    private String mSDescription;
    @SerializedName("sJobId")
    private String mSJobId;
    @SerializedName("sStatus")
    private String mSStatus;
    @SerializedName("sType")
    private String mSType;
    @SerializedName("user")
    private UserProfile mUser;
    @SerializedName("_id")
    private String m_id;

    public String getDCreatedDate() {
        return mDCreatedDate;
    }

    public void setDCreatedDate(String dCreatedDate) {
        mDCreatedDate = dCreatedDate;
    }

    public String getSDescription() {
        return mSDescription;
    }

    public void setSDescription(String sDescription) {
        mSDescription = sDescription;
    }

    public String getSJobId() {
        return mSJobId;
    }

    public void setSJobId(String sJobId) {
        mSJobId = sJobId;
    }

    public String getSStatus() {
        return mSStatus;
    }

    public void setSStatus(String sStatus) {
        mSStatus = sStatus;
    }

    public String getSType() {
        return mSType;
    }

    public void setSType(String sType) {
        mSType = sType;
    }

    public UserProfile getUser() {
        return mUser;
    }

    public void setUser(UserProfile user) {
        mUser = user;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
