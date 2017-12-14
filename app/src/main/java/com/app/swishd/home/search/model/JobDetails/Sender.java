package com.app.swishd.home.search.model.JobDetails;

import com.app.swishd.utility.Utility;
import com.google.gson.annotations.SerializedName;


public class Sender {

    @SerializedName("profile_image")
    private String profileImage;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("_id")
    private String id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
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
        return Utility.nullCheck(username);
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
                        ",_id = '" + id + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        ",email = '" + email + '\'' +
                        ",username = '" + username + '\'' +
                        "}";
    }
}