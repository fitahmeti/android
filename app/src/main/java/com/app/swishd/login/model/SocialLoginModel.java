
package com.app.swishd.login.model;

import com.google.gson.annotations.SerializedName;

public class SocialLoginModel {

    @SerializedName("Authorization")
    private String mAuthorization;
    @SerializedName("message")
    private String mMessage;

    public String getAuthorization() {
        return mAuthorization;
    }

    public void setAuthorization(String Authorization) {
        mAuthorization = Authorization;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
