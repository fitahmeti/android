
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

public class Activity {

    @SerializedName("dMessageDate")
    private String mDMessageDate;
    @SerializedName("dSwishDate")
    private Object mDSwishDate;
    @SerializedName("eActivityStatus")
    private String mEActivityStatus;
    @SerializedName("messageSender")
    private MessageSender mMessageSender;
    @SerializedName("sMessage")
    private String mSMessage;
    @SerializedName("sender")
    private Sender mSender;
    @SerializedName("_id")
    private String m_id;

    public String getDMessageDate() {
        return mDMessageDate;
    }

    public void setDMessageDate(String dMessageDate) {
        mDMessageDate = dMessageDate;
    }

    public Object getDSwishDate() {
        return mDSwishDate;
    }

    public void setDSwishDate(Object dSwishDate) {
        mDSwishDate = dSwishDate;
    }

    public String getEActivityStatus() {
        return mEActivityStatus;
    }

    public void setEActivityStatus(String eActivityStatus) {
        mEActivityStatus = eActivityStatus;
    }

    public MessageSender getMessageSender() {
        return mMessageSender;
    }

    public void setMessageSender(MessageSender messageSender) {
        mMessageSender = messageSender;
    }

    public String getSMessage() {
        if (mEActivityStatus == null || mEActivityStatus.isEmpty())
            return "";
        else if ("message".equalsIgnoreCase(mEActivityStatus))
            return mSMessage;
        else if ("drop_office".equalsIgnoreCase(mEActivityStatus))
            return getMessageSender().getUsername() + " has dropped the item at the pickup collection point.";
        else if ("pickup_office".equalsIgnoreCase(mEActivityStatus))
            return getMessageSender().getUsername() + " has picked item from collection point.";
        else if ("deliver_office".equalsIgnoreCase(mEActivityStatus))
            return getMessageSender().getUsername() + " has dropped item to collection point.";
        else if ("deliver_receiver".equalsIgnoreCase(mEActivityStatus))
            return getMessageSender().getUsername() + " has picked item from delivery collection point.";
        else if ("pickup".equalsIgnoreCase(mEActivityStatus))
            return getMessageSender().getUsername() + " has picked item from user";
        else if ("deliver".equalsIgnoreCase(mEActivityStatus))
            return getMessageSender().getUsername() + " has deliver item to user";
        else if ("job_accept".equalsIgnoreCase(mEActivityStatus))
            return getMessageSender().getUsername() + " has accept " + getSender().getUsername() + "'s job request.";
        else
            return mEActivityStatus;
    }

    public void setSMessage(String sMessage) {
        mSMessage = sMessage;
    }

    public Sender getSender() {
        return mSender;
    }

    public void setSender(Sender sender) {
        mSender = sender;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

    public class MessageSender {

        @SerializedName("sUserId")
        private String mSUserId;
        @SerializedName("username")
        private String mUsername;

        public String getSUserId() {
            return mSUserId;
        }

        public void setSUserId(String sUserId) {
            mSUserId = sUserId;
        }

        public String getUsername() {
            return mUsername;
        }

        public void setUsername(String username) {
            mUsername = username;
        }

    }

    public class Sender {

        @SerializedName("profile_image")
        private String mProfileImage;
        @SerializedName("sUserId")
        private String mSUserId;
        @SerializedName("username")
        private String mUsername;

        public String getProfileImage() {
            return mProfileImage;
        }

        public void setProfileImage(String profileImage) {
            mProfileImage = profileImage;
        }

        public String getSUserId() {
            return mSUserId;
        }

        public void setSUserId(String sUserId) {
            mSUserId = sUserId;
        }

        public String getUsername() {
            return mUsername;
        }

        public void setUsername(String username) {
            mUsername = username;
        }

    }
}
