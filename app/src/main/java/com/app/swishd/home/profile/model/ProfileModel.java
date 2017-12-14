
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

public class ProfileModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("userProfile")
    private UserProfile mUserProfile;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public UserProfile getUserProfile() {
        return mUserProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        mUserProfile = userProfile;
    }
}
