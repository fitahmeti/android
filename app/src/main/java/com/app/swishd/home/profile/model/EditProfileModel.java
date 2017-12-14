
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EditProfileModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("userProfile")
    private List<UserProfile> userProfileList;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<UserProfile> getUserProfileList() {
        return userProfileList;
    }

    public void setUserProfileList(List<UserProfile> userProfileList) {
        this.userProfileList = userProfileList;
    }
}
