
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

public class Sender {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("linkedin_id")
    private String mLinkedinId;
    @SerializedName("username")
    private String mUsername;
    @SerializedName("_id")
    private String m_id;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getLinkedinId() {
        return mLinkedinId;
    }

    public void setLinkedinId(String linkedinId) {
        mLinkedinId = linkedinId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
