
package com.app.swishd.home.profile.activity.contact.model;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("sMessage")
    private String mSMessage;
    @SerializedName("sSubject")
    private String mSSubject;
    @SerializedName("_id")
    private String m_id;

    public String getSMessage() {
        return mSMessage;
    }

    public void setSMessage(String sMessage) {
        mSMessage = sMessage;
    }

    public String getSSubject() {
        return mSSubject;
    }

    public void setSSubject(String sSubject) {
        mSSubject = sSubject;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
