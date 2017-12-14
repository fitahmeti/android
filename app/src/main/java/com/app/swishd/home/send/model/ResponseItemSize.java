package com.app.swishd.home.send.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseItemSize {

    @SerializedName("message")
    private String message;

    @SerializedName("totalCount")
    private int totalCount;

    @SerializedName("items")
    private List<ItemsItem> items;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ItemsItem> getItems() {
        return items;
    }

    public void setItems(List<ItemsItem> items) {
        this.items = items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return
                "ResponseItemSize{" +
                        "message = '" + message + '\'' +
                        ",items = '" + items + '\'' +
                        "}";
    }

    public class ItemsItem {

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
            return sSizeTitle;
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
                    "ItemsItem{" +
                            "sSizeDescription = '" + sSizeDescription + '\'' +
                            ",sOriginalSizePicture = '" + sOriginalSizePicture + '\'' +
                            ",sSizeTitle = '" + sSizeTitle + '\'' +
                            ",sSort = '" + sSort + '\'' +
                            ",_id = '" + id + '\'' +
                            "}";
        }
    }
}