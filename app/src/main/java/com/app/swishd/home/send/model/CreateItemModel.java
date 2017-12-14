package com.app.swishd.home.send.model;


import java.io.Serializable;

public class CreateItemModel
        implements Serializable {

    private String sJobTitle;
    private String sSizeId;
    private String sSizeType;
    private double sPriceValue = 50;
    private String sPickOfficeId;
    private double sPickLatitude;
    private double sPickLongitude;
    private String sPickAddress;
    private String sPickDateTime = null;
    private String sDropOfficeId;
    private double sDropLatitude;
    private double sDropLongitude;
    private String sDropAddress;
    private String sDropDateTime = null;
    private double sRecommendedPrice = 8;
    private double sRewardPrice = 4;
    private double sInsuranceFee = 3;
    private double sServiceTax = 0;
    private double sVat = 1;
    private char eStatus = 'y';
    private long mPickDate;
    private long mDropDate;

    public String getsRecommendedPrice() {
        return String.valueOf(sRecommendedPrice);
    }

    public double getsRecommendedPriceValue() {
        return sRecommendedPrice;
    }

    public void setsRecommendedPriceAndSetRewardPrice(double value) {
        sRecommendedPrice = value;
        sRewardPrice = sRecommendedPrice - sVat - sInsuranceFee - sServiceTax;
        if (sRewardPrice < 0)
            sRewardPrice = 0;
    }

    public long getmPickDate() {
        return mPickDate;
    }

    public void setmPickDate(long mPickDate) {
        this.mPickDate = mPickDate;
    }

    public long getmDropDate() {
        return mDropDate;
    }

    public void setmDropDate(long mDropDate) {
        this.mDropDate = mDropDate;
    }

    public String getsJobTitle() {
        return sJobTitle;
    }

    public void setsJobTitle(String sJobTitle) {
        this.sJobTitle = sJobTitle;
    }

    public String getsSizeId() {
        return sSizeId;
    }

    public void setsSizeId(String sSizeId) {
        this.sSizeId = sSizeId;
    }

    public double getsPriceValue() {
        return sPriceValue;
    }

    public void setsPriceValue(double sPriceValue) {
        this.sPriceValue = sPriceValue;
    }

    public String getsPickOfficeId() {
        return sPickOfficeId;
    }

    public void setsPickOfficeId(String sPickOfficeId) {
        this.sPickOfficeId = sPickOfficeId;
    }

    public double getsPickLatitude() {
        return sPickLatitude;
    }

    public void setsPickLatitude(double sPickLatitude) {
        this.sPickLatitude = sPickLatitude;
    }

    public double getsPickLongitude() {
        return sPickLongitude;
    }

    public void setsPickLongitude(double sPickLongitude) {
        this.sPickLongitude = sPickLongitude;
    }

    public String getsPickAddress() {
        return sPickAddress;
    }

    public void setsPickAddress(String sPickAddress) {
        this.sPickAddress = sPickAddress;
    }

    public String getsPickDateTime() {
        return sPickDateTime;
    }

    public void setsPickDateTime(String sPickDateTime) {
        this.sPickDateTime = sPickDateTime;
    }

    public String getsDropOfficeId() {
        return sDropOfficeId;
    }

    public void setsDropOfficeId(String sDropOfficeId) {
        this.sDropOfficeId = sDropOfficeId;
    }

    public double getsDropLatitude() {
        return sDropLatitude;
    }

    public void setsDropLatitude(double sDropLatitude) {
        this.sDropLatitude = sDropLatitude;
    }

    public double getsDropLongitude() {
        return sDropLongitude;
    }

    public void setsDropLongitude(double sDropLongitude) {
        this.sDropLongitude = sDropLongitude;
    }

    public String getsDropAddress() {
        return sDropAddress;
    }

    public void setsDropAddress(String sDropAddress) {
        this.sDropAddress = sDropAddress;
    }

    public String getsDropDateTime() {
        return sDropDateTime;
    }

    public void setsDropDateTime(String sDropDateTime) {
        this.sDropDateTime = sDropDateTime;
    }

    public double getsRewardPrice() {
        return sRewardPrice;
    }

    public void setsRewardPrice(double sRewardPrice) {
        this.sRewardPrice = sRewardPrice;
    }

    public String getsRewardPriceString() {
        return String.valueOf(sRewardPrice);
    }

    public String getTotalFee() {
        return (sServiceTax + sInsuranceFee) + "";
    }

    public double getsInsuranceFee() {
        return sInsuranceFee;
    }

    public void setsInsuranceFee(double sInsuranceFee) {
        this.sInsuranceFee = sInsuranceFee;
    }

    public double getsServiceTax() {
        return sServiceTax;
    }

    public void setsServiceTax(double sServiceTax) {
        this.sServiceTax = sServiceTax;
    }

    public double getsVat() {
        return sVat;
    }

    public void setsVat(double sVat) {
        this.sVat = sVat;
    }

    public char geteStatus() {
        return eStatus;
    }

    public void seteStatus(char eStatus) {
        this.eStatus = eStatus;
    }

    @Override
    public String toString() {
        return "Title ==>" + sJobTitle + " ," +
                "sSizeId ==>" + sSizeId + " ," +
                "sPriceValue ==>" + sPriceValue + " ," +
                "sPickOfficeId ==>" + sPickOfficeId + " ," +
                "sPickLatitude ==>" + sPickLatitude + " ," +
                "sPickLongitude ==>" + sPickLongitude + " ," +
                "sPickAddress ==>" + sPickAddress + " ," +
                "sPickDateTime ==>" + sPickDateTime + " ," +
                "sDropOfficeId ==>" + sDropOfficeId + " ," +
                "sDropLatitude ==>" + sDropLatitude + " ," +
                "sDropLongitude ==>" + sDropLongitude + " ," +
                "sDropAddress ==>" + sDropAddress + " ," +
                "sDropDateTime ==>" + sDropDateTime + " ," +
                "sRewardPrice ==>" + sRewardPrice + " ," +
                "sInsuranceFee ==>" + sInsuranceFee + " ," +
                "sServiceTax ==>" + sServiceTax + " ," +
                "sVat ==>" + sVat + " ," +
                "eStatus ==>" + eStatus + "";
    }

    public String getVatString() {
        return sVat + "";
    }

    public String getsSizeType() {
        return sSizeType;
    }

    public void setsSizeType(String sSizeType) {
        this.sSizeType = sSizeType;
    }

    public boolean isDateTimeSelected(String value) {
        return value == null;
    }
}
