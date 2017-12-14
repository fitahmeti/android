
package com.app.swishd.home.notification.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("data")
    private List<Notification> mNotifications;
    @SerializedName("total_unread")
    private int mTotalUnread;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<Notification> getNotifications() {
        return mNotifications;
    }

    public void setNotifications(List<Notification> notifications) {
        mNotifications = notifications;
    }

    public int getTotalUnread() {
        return mTotalUnread;
    }

    public void setTotalUnread(int totalUnread) {
        mTotalUnread = totalUnread;
    }

}
