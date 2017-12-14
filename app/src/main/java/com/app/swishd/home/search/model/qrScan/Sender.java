package com.app.swishd.home.search.model.qrScan;

import com.app.swishd.utility.Utility;
import com.google.gson.annotations.SerializedName;


public class Sender {

    @SerializedName("profile_image")
    private Object profileImage;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("linkedin_id")
    private String linkedinId;

    @SerializedName("_id")
    private String id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    public Object getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Object profileImage) {
        this.profileImage = profileImage;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLinkedinId() {
        return linkedinId;
    }

    public void setLinkedinId(String linkedinId) {
        this.linkedinId = linkedinId;
    }

    public String getId() {
        return Utility.nullCheck(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return
                "Sender{" +
                        "profile_image = '" + profileImage + '\'' +
                        ",last_name = '" + lastName + '\'' +
                        ",linkedin_id = '" + linkedinId + '\'' +
                        ",_id = '" + id + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        ",email = '" + email + '\'' +
                        ",username = '" + username + '\'' +
                        "}";
    }
}