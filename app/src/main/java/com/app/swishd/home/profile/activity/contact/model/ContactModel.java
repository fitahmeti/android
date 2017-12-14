
package com.app.swishd.home.profile.activity.contact.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("items")
    private List<Message> mMessages;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<Message> getMessages() {
        return mMessages;
    }

    public void setMessages(List<Message> messages) {
        mMessages = messages;
    }

}
