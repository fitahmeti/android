
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

public class CountryCode {

    @SerializedName("code")
    private String mCode;
    @SerializedName("dial_code")
    private String mDialCode;
    @SerializedName("name")
    private String mName;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getDialCode() {
        return mDialCode;
    }

    public void setDialCode(String dialCode) {
        mDialCode = dialCode;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
