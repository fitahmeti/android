
package com.app.swishd.home.profile.model;

import com.app.swishd.retrofit.call.Api;
import com.google.gson.annotations.SerializedName;

public class JobSize {

    @SerializedName("sOriginalSizePicture")
    private String sizePicture;
    @SerializedName("sSizeDescription")
    private String mSSizeDescription;
    @SerializedName("sSizeTitle")
    private String mSSizeTitle;
    @SerializedName("_id")
    private String m_id;

    public String getSSizeDescription() {
        return mSSizeDescription;
    }

    public void setSSizeDescription(String sSizeDescription) {
        mSSizeDescription = sSizeDescription;
    }

    public String getSSizeTitle() {
        return mSSizeTitle;
    }

    public void setSSizeTitle(String sSizeTitle) {
        mSSizeTitle = sSizeTitle;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

    public String getSizePicture() {
        return Api.BASE_URL_IMAGE + sizePicture;
    }

    public void setSizePicture(String sizePicture) {
        this.sizePicture = sizePicture;
    }
}
