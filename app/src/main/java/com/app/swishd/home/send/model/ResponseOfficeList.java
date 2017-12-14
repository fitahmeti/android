package com.app.swishd.home.send.model;

import com.app.swishd.utility.Utility;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Locale;

public class ResponseOfficeList {

    @SerializedName("detail")
    private List<DetailItem> detail;

    @SerializedName("message")
    private String message;

    public List<DetailItem> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailItem> detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return
                "ResponseOfficeList{" +
                        "detail = '" + detail + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }

    public class OHoursItem {

        @SerializedName("close")
        private int close;

        @SerializedName("day")
        private int day;

        @SerializedName("open")
        private int open;

        public int getClose() {
            return close;
        }

        public void setClose(int close) {
            this.close = close;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getOpen() {
            return open;
        }

        public void setOpen(int open) {
            this.open = open;
        }

        @Override
        public String toString() {
            return
                    "OHoursItem{" +
                            "close = '" + close + '\'' +
                            ",day = '" + day + '\'' +
                            ",open = '" + open + '\'' +
                            "}";
        }
    }

    public class DetailItem {

        @SerializedName("workStatus")
        private String workStatus;

        @SerializedName("oLoc")
        private List<Double> oLoc;

        @SerializedName("distance")
        private double distance;

        @SerializedName("sCountry")
        private String sCountry;

        @SerializedName("sZipCode")
        private String sZipCode;

        @SerializedName("oHours")
        private List<OHoursItem> oHours;

        @SerializedName("sAddressLine2")
        private String sAddressLine2;

        @SerializedName("sCity")
        private String sCity;

        @SerializedName("_id")
        private String id;

        @SerializedName("sAddressLine1")
        private String sAddressLine1;

        @SerializedName("sState")
        private String sState;

        @SerializedName("sOfficeName")
        private String sOfficeName;

        public String getWorkStatus() {
            return workStatus;
        }

        public void setWorkStatus(String workStatus) {
            this.workStatus = workStatus;
        }

        public List<Double> getOLoc() {
            return oLoc;
        }

        public void setOLoc(List<Double> oLoc) {
            this.oLoc = oLoc;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getSCountry() {
            return Utility.nullCheck(sCountry);
        }

        public void setSCountry(String sCountry) {
            this.sCountry = sCountry;
        }

        public String getSZipCode() {
            return Utility.nullCheck(sZipCode);
        }

        public void setSZipCode(String sZipCode) {
            this.sZipCode = sZipCode;
        }

        public List<OHoursItem> getOHours() {
            return oHours;
        }

        public void setOHours(List<OHoursItem> oHours) {
            this.oHours = oHours;
        }

        public String getSAddressLine2() {
            return Utility.nullCheck(sAddressLine2);
        }

        public void setSAddressLine2(String sAddressLine2) {
            this.sAddressLine2 = sAddressLine2;
        }

        public String getSCity() {
            return Utility.nullCheck(sCity);
        }

        public void setSCity(String sCity) {
            this.sCity = sCity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSAddressLine1() {
            return Utility.nullCheck(sAddressLine1);
        }

        public void setSAddressLine1(String sAddressLine1) {
            this.sAddressLine1 = sAddressLine1;
        }

        public String getSState() {
            return sState;
        }

        public void setSState(String sState) {
            this.sState = sState;
        }

        public String getSOfficeName() {
            return sOfficeName;
        }

        public void setSOfficeName(String sOfficeName) {
            this.sOfficeName = sOfficeName;
        }

        public String getDistanceString() {
            return String.format(Locale.getDefault(), "%.2f", distance) + " KM";
        }

        @Override
        public String toString() {
            return
                    "DetailItem{" +
                            "workStatus = '" + workStatus + '\'' +
                            ",oLoc = '" + oLoc + '\'' +
                            ",distance = '" + distance + '\'' +
                            ",sCountry = '" + sCountry + '\'' +
                            ",sZipCode = '" + sZipCode + '\'' +
                            ",oHours = '" + oHours + '\'' +
                            ",sAddressLine2 = '" + sAddressLine2 + '\'' +
                            ",sCity = '" + sCity + '\'' +
                            ",_id = '" + id + '\'' +
                            ",sAddressLine1 = '" + sAddressLine1 + '\'' +
                            ",sState = '" + sState + '\'' +
                            ",sOfficeName = '" + sOfficeName + '\'' +
                            "}";
        }
    }

}