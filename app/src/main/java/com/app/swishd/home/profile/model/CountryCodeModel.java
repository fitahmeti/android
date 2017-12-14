
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

public class CountryCodeModel {

    @SerializedName("list")
    private java.util.List<CountryCode> mCountryCode;

    public java.util.List<CountryCode> getList() {
        return mCountryCode;
    }

    public void setList(java.util.List<CountryCode> countryCode) {
        mCountryCode = countryCode;
    }

}
