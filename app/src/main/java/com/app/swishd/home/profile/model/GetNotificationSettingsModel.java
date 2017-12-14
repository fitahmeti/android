
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetNotificationSettingsModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("data")
    private List<NotificationSetting> mNotificationSetting;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<NotificationSetting> getNotificationSetting() {
        return mNotificationSetting;
    }

    public void setNotificationSetting(List<NotificationSetting> NotificationSetting) {
        mNotificationSetting = NotificationSetting;
    }

}
