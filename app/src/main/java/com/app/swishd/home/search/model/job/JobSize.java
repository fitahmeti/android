package com.app.swishd.home.search.model.job;

import com.app.swishd.utility.Utility;
import com.google.gson.annotations.SerializedName;

public class JobSize {

    @SerializedName("sSizeDescription")
    private String sSizeDescription;

    @SerializedName("sOriginalSizePicture")
    private String sOriginalSizePicture;

    @SerializedName("sSizeTitle")
    private String sSizeTitle;

    @SerializedName("sSort")
    private int sSort;

    @SerializedName("_id")
    private String id;

    public String getSSizeDescription() {
        return sSizeDescription;
    }

    public void setSSizeDescription(String sSizeDescription) {
        this.sSizeDescription = sSizeDescription;
    }

    public String getSOriginalSizePicture() {
        return sOriginalSizePicture;
    }

    public void setSOriginalSizePicture(String sOriginalSizePicture) {
        this.sOriginalSizePicture = sOriginalSizePicture;
    }

    public String getSSizeTitle() {
        return Utility.nullCheck(sSizeTitle);
    }

    public void setSSizeTitle(String sSizeTitle) {
        this.sSizeTitle = sSizeTitle;
    }

    public int getSSort() {
        return sSort;
    }

    public void setSSort(int sSort) {
        this.sSort = sSort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "JobSize{" +
                        "sSizeDescription = '" + sSizeDescription + '\'' +
                        ",sOriginalSizePicture = '" + sOriginalSizePicture + '\'' +
                        ",sSizeTitle = '" + sSizeTitle + '\'' +
                        ",sSort = '" + sSort + '\'' +
                        ",_id = '" + id + '\'' +
                        "}";
    }
}