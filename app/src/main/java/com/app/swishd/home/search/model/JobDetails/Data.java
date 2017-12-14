package com.app.swishd.home.search.model.JobDetails;

import android.content.Context;

import com.app.swishd.R;
import com.app.swishd.home.profile.model.ReceivedBy;
import com.app.swishd.home.search.model.job.JobSize;
import com.app.swishd.utility.DateUtil;
import com.app.swishd.utility.Utility;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("oDropLoc")
    private List<Double> oDropLoc;

    @SerializedName("sVat")
    private double sVat;

    @SerializedName("swishr")
    private Swishr swishr;

    @SerializedName("pickOffice")
    private Object pickOffice;

    @SerializedName("jobRequestCount")
    private int jobRequestCount;

    @SerializedName("qrcode")
    private List<QRCode> qrcode;

    @SerializedName("sPickAddress")
    private String sPickAddress;

    @SerializedName("dropOffice")
    private Object dropOffice;

    @SerializedName("sPickDateTime")
    private String sPickDateTime;

    @SerializedName("eJobStatus")
    private String eJobStatus;

    @SerializedName("sDropAddress")
    private String sDropAddress;

    @SerializedName("sDropDateTime")
    private String sDropDateTime;

    @SerializedName("jobSize")
    private JobSize jobSize;

    @SerializedName("sender")
    private Sender sender;

    @SerializedName("jobRequested")
    private double jobRequested;

    @SerializedName("sRewardPrice")
    private double sRewardPrice;

    @SerializedName("oPickLoc")
    private List<Double> oPickLoc;

    @SerializedName("ePickImmediately")
    private String ePickImmediately;

    @SerializedName("sInsuranceFee")
    private double sInsuranceFee;

    @SerializedName("sJobTitle")
    private String sJobTitle;

    @SerializedName("_id")
    private String id;

    @SerializedName("sRecommendedPrice")
    private double sRecommendedPrice;

    @SerializedName("order")
    private double order;
    @SerializedName("sRecievedBy")
    private ReceivedBy sRecievedBy;
    @SerializedName("sPickupBy")
    private ReceivedBy sPickupBy;

    public List<Double> getODropLoc() {
        return oDropLoc;
    }

    public void setODropLoc(List<Double> oDropLoc) {
        this.oDropLoc = oDropLoc;
    }

    public double getSVat() {
        return sVat;
    }

    public void setSVat(double sVat) {
        this.sVat = sVat;
    }

    public Swishr getSwishr() {
        return swishr;
    }

    public void setSwishr(Swishr swishr) {
        this.swishr = swishr;
    }

    public Object getPickOffice() {
        return pickOffice;
    }

    public void setPickOffice(Object pickOffice) {
        this.pickOffice = pickOffice;
    }

    public int getJobRequestCount() {
        return jobRequestCount;
    }

    public void setJobRequestCount(int jobRequestCount) {
        this.jobRequestCount = jobRequestCount;
    }

    public List<QRCode> getQrcode() {
        return qrcode;
    }

    public void setQrcode(List<QRCode> qrcode) {
        this.qrcode = qrcode;
    }

    public String getSPickAddress() {
        return Utility.nullCheck(sPickAddress);
    }

    public void setSPickAddress(String sPickAddress) {
        this.sPickAddress = sPickAddress;
    }

    public Object getDropOffice() {
        return dropOffice;
    }

    public void setDropOffice(Object dropOffice) {
        this.dropOffice = dropOffice;
    }

    public String getSPickDateTime() {
        return sPickDateTime;
    }

    public void setSPickDateTime(String sPickDateTime) {
        this.sPickDateTime = sPickDateTime;
    }

    public String getEJobStatus() {
        return eJobStatus;
    }

    public void setEJobStatus(String eJobStatus) {
        this.eJobStatus = eJobStatus;
    }

    public String getSDropAddress() {
        return Utility.nullCheck(sDropAddress);
    }

    public void setSDropAddress(String sDropAddress) {
        this.sDropAddress = sDropAddress;
    }

    public JobSize getJobSize() {
        return jobSize;
    }

    public void setJobSize(JobSize jobSize) {
        this.jobSize = jobSize;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public double getJobRequested() {
        return jobRequested;
    }

    public boolean isJobRequested() {
        return jobRequested == 1;
    }

    public void setJobRequested(double jobRequested) {
        this.jobRequested = jobRequested;
    }

    public double getSRewardPrice() {
        return sRewardPrice;
    }

    public void setSRewardPrice(double sRewardPrice) {
        this.sRewardPrice = sRewardPrice;
    }

    public List<Double> getOPickLoc() {
        return oPickLoc;
    }

    public void setOPickLoc(List<Double> oPickLoc) {
        this.oPickLoc = oPickLoc;
    }

    public String getEPickImmediately() {
        return ePickImmediately;
    }

    public void setEPickImmediately(String ePickImmediately) {
        this.ePickImmediately = ePickImmediately;
    }

    public double getSInsuranceFee() {
        return sInsuranceFee;
    }

    public void setSInsuranceFee(double sInsuranceFee) {
        this.sInsuranceFee = sInsuranceFee;
    }

    public String getSJobTitle() {
        return Utility.nullCheck(sJobTitle);
    }

    public void setSJobTitle(String sJobTitle) {
        this.sJobTitle = sJobTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getSRecommendedPrice() {
        return sRecommendedPrice;
    }

    public void setSRecommendedPrice(double sRecommendedPrice) {
        this.sRecommendedPrice = sRecommendedPrice;
    }

    public double getOrder() {
        return order;
    }

    public void setOrder(double order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "oDropLoc = '" + oDropLoc + '\'' +
                        ",sVat = '" + sVat + '\'' +
                        ",swishr = '" + swishr + '\'' +
                        ",pickOffice = '" + pickOffice + '\'' +
                        ",jobRequestCount = '" + jobRequestCount + '\'' +
                        ",qrcode = '" + qrcode + '\'' +
                        ",sPickAddress = '" + sPickAddress + '\'' +
                        ",dropOffice = '" + dropOffice + '\'' +
                        ",sPickDateTime = '" + sPickDateTime + '\'' +
                        ",eJobStatus = '" + eJobStatus + '\'' +
                        ",sDropAddress = '" + sDropAddress + '\'' +
                        ",jobSize = '" + jobSize + '\'' +
                        ",sender = '" + sender + '\'' +
                        ",jobRequested = '" + jobRequested + '\'' +
                        ",sRewardPrice = '" + sRewardPrice + '\'' +
                        ",oPickLoc = '" + oPickLoc + '\'' +
                        ",ePickImmediately = '" + ePickImmediately + '\'' +
                        ",sInsuranceFee = '" + sInsuranceFee + '\'' +
                        ",sJobTitle = '" + sJobTitle + '\'' +
                        ",_id = '" + id + '\'' +
                        ",sRecommendedPrice = '" + sRecommendedPrice + '\'' +
                        ",order = '" + order + '\'' +
                        "}";
    }

    public String getPickUpDate(Context v) {
        if (sPickDateTime == null)
            return v.getResources().getString(R.string.flexible);
        return DateUtil.getFormatedDate(sPickDateTime, DateUtil.SERVER_DATE, "dd. MMM hh:mm a");
    }

    public String getDropDate(Context v) {
        if (sDropDateTime == null)
            return v.getResources().getString(R.string.flexible);
        return DateUtil.getFormatedDate(sDropDateTime, DateUtil.SERVER_DATE, "dd. MMM hh:mm a");
    }

    public String getFormattedPickDate() {
        if (sPickDateTime == null || sPickDateTime.isEmpty())
            return "Flexible";
        else
            return DateUtil.getFormatedDate(sPickDateTime, DateUtil.SERVER_DATE, "EEE dd MMM");
    }

    public String getFormattedPickDateReceipt() {
        if (sPickDateTime == null || sPickDateTime.isEmpty())
            return "Flexible";
        else
            return DateUtil.getFormatedDate(sPickDateTime, DateUtil.SERVER_DATE, "EEE dd MMM");
    }

    public String getFormattedPickTime() {
        if (sPickDateTime == null || sPickDateTime.isEmpty())
            return "Flexible";
        else
            return DateUtil.getFormatedDate(sPickDateTime, DateUtil.SERVER_DATE, "hh:mm a");
    }

    public String getFormattedDropDate() {
        if (sDropDateTime == null || sDropDateTime.isEmpty())
            return "Flexible";
        else
            return DateUtil.getFormatedDate(sDropDateTime, DateUtil.SERVER_DATE, "EEE dd MMM");
    }

    public String getFormattedDropDateReceipt() {
        if (sDropDateTime == null || sDropDateTime.isEmpty())
            return "Flexible";
        else
            return DateUtil.getFormatedDate(sDropDateTime, DateUtil.SERVER_DATE, "EEE dd MMM");
    }

    public String getFormattedDropTime() {
        if (sDropDateTime == null || sDropDateTime.isEmpty())
            return "Flexible";
        else
            return DateUtil.getFormatedDate(sDropDateTime, DateUtil.SERVER_DATE, "hh:mm a");
    }

    public ReceivedBy getsRecievedBy() {
        return sRecievedBy;
    }

    public void setsRecievedBy(ReceivedBy sRecievedBy) {
        this.sRecievedBy = sRecievedBy;
    }

    public ReceivedBy getsPickupBy() {
        return sPickupBy;
    }

    public void setsPickupBy(ReceivedBy sPickupBy) {
        this.sPickupBy = sPickupBy;
    }
}