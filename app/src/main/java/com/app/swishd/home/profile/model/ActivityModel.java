
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivityModel {

    @SerializedName("data")
    private List<Activity> mActivities;
    @SerializedName("message")
    private String mMessage;

    public List<Activity> getActivities() {
        return mActivities;
    }

    public void setActivities(List<Activity> activities) {
        mActivities = activities;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
