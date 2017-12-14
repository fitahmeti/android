package com.app.swishd.home.profile.model;

import android.databinding.BindingAdapter;

import com.app.swishd.utility.DateUtil;
import com.app.swishd.widget.ProfileSendrStatusView;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Job {

    @SerializedName("dropOffice")
    private Object mDropOffice;
    @SerializedName("eJobStatus")
    private String mEJobStatus;
    @SerializedName("ePickImmediately")
    private String mEPickImmediately;
    @SerializedName("jobRequestCount")
    private Double mJobRequestCount;
    @SerializedName("jobRequested")
    private Double mJobRequested;
    @SerializedName("jobSize")
    private JobSize mJobSize;
    @SerializedName("oDropLoc")
    private List<Double> mODropLoc;
    @SerializedName("oPickLoc")
    private List<Double> mOPickLoc;
    @SerializedName("order")
    private int mOrder;
    @SerializedName("sPickDateTime")
    private String mSPickDateTime;
    @SerializedName("sDropDateTime")
    private String mSDropDateTime;
    @SerializedName("pickOffice")
    private Object mPickOffice;
    @SerializedName("sDropAddress")
    private String mSDropAddress;
    @SerializedName("sInsuranceFee")
    private Double mSInsuranceFee;
    @SerializedName("sJobTitle")
    private String mSJobTitle;
    @SerializedName("sPickAddress")
    private String mSPickAddress;
    @SerializedName("sRecommendedPrice")
    private Double mSRecommendedPrice;
    @SerializedName("sRewardPrice")
    private Double mSRewardPrice;
    @SerializedName("sVat")
    private Double mSVat;
    @SerializedName("sender")
    private Sender mSender;
    @SerializedName("swishr")
    private Object mSwishr;
    @SerializedName("_id")
    private String m_id;
    @SerializedName("sRecievedBy")
    private ReceivedBy sRecievedBy;
    @SerializedName("sPickupBy")
    private ReceivedBy sPickupBy;

    @BindingAdapter("pssv_text")
    public static void setStatusText(ProfileSendrStatusView v, String textStr) {
        v.setText(textStr);
    }

    public Object getDropOffice() {
        return mDropOffice;
    }

    public void setDropOffice(Object dropOffice) {
        mDropOffice = dropOffice;
    }

    public String getEJobStatus() {
        return mEJobStatus;
    }

    public void setEJobStatus(String eJobStatus) {
        mEJobStatus = eJobStatus;
    }

    public String getEPickImmediately() {
        return mEPickImmediately;
    }

    public void setEPickImmediately(String ePickImmediately) {
        mEPickImmediately = ePickImmediately;
    }

    public Double getJobRequestCount() {
        return mJobRequestCount;
    }

    public void setJobRequestCount(Double jobRequestCount) {
        mJobRequestCount = jobRequestCount;
    }

    public Double getJobRequested() {
        return mJobRequested;
    }

    public void setJobRequested(Double jobRequested) {
        mJobRequested = jobRequested;
    }

    public JobSize getJobSize() {
        return mJobSize;
    }

    public void setJobSize(JobSize jobSize) {
        mJobSize = jobSize;
    }

    public List<Double> getODropLoc() {
        return mODropLoc;
    }

    public void setODropLoc(List<Double> oDropLoc) {
        mODropLoc = oDropLoc;
    }

    public List<Double> getOPickLoc() {
        return mOPickLoc;
    }

    public void setOPickLoc(List<Double> oPickLoc) {
        mOPickLoc = oPickLoc;
    }

    public int getOrder() {
        return mOrder;
    }

    public void setOrder(int order) {
        mOrder = order;
    }

    public Object getPickOffice() {
        return mPickOffice;
    }

    public void setPickOffice(Object pickOffice) {
        mPickOffice = pickOffice;
    }

    public String getSDropAddress() {
        return mSDropAddress;
    }

    public void setSDropAddress(String sDropAddress) {
        mSDropAddress = sDropAddress;
    }

    public Double getSInsuranceFee() {
        return mSInsuranceFee;
    }

    public void setSInsuranceFee(Double sInsuranceFee) {
        mSInsuranceFee = sInsuranceFee;
    }

    public String getSJobTitle() {
        return mSJobTitle;
    }

    public void setSJobTitle(String sJobTitle) {
        mSJobTitle = sJobTitle;
    }

    public String getSPickAddress() {
        return mSPickAddress;
    }

    public void setSPickAddress(String sPickAddress) {
        mSPickAddress = sPickAddress;
    }

    public Double getSRecommendedPrice() {
        return mSRecommendedPrice;
    }

    public void setSRecommendedPrice(Double sRecommendedPrice) {
        mSRecommendedPrice = sRecommendedPrice;
    }

    public Double getSRewardPrice() {
        return mSRewardPrice;
    }

    public void setSRewardPrice(Double sRewardPrice) {
        mSRewardPrice = sRewardPrice;
    }

    public Double getSVat() {
        return mSVat;
    }

    public void setSVat(Double sVat) {
        mSVat = sVat;
    }

    public Sender getSender() {
        return mSender;
    }

    public void setSender(Sender sender) {
        mSender = sender;
    }

    public Object getSwishr() {
        return mSwishr;
    }

    public void setSwishr(Object swishr) {
        mSwishr = swishr;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

    public String getmSPickDateTime() {
        return mSPickDateTime;
    }

    public void setmSPickDateTime(String mSPickDateTime) {
        this.mSPickDateTime = mSPickDateTime;
    }

    public String getmSDropDateTime() {
        return mSDropDateTime;
    }

    public void setmSDropDateTime(String mSDropDateTime) {
        this.mSDropDateTime = mSDropDateTime;
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

    //.....User defined propertied and functions .......//
    public String getFormattedRecommandedPrice() {
        if (mSRecommendedPrice == null)
            return "0.0";
        else
            return String.format("%.1f", mSRecommendedPrice.doubleValue());
    }

    public String getFormattedPickDate() {
        if (mSPickDateTime == null || mSPickDateTime.isEmpty())
            return "Flexible";
        else
            return DateUtil.getFormatedDate(mSPickDateTime, DateUtil.SERVER_DATE, "EEE dd MMM");
    }

    public String getFormattedPickTime() {
        if (mSPickDateTime == null || mSPickDateTime.isEmpty())
            return "Flexible";
        else
            return DateUtil.getFormatedDate(mSPickDateTime, DateUtil.SERVER_DATE, "hh:mm a");
    }

    public String getFormattedDropDate() {
        if (mSDropDateTime == null || mSDropDateTime.isEmpty())
            return "Flexible";
        else
            return DateUtil.getFormatedDate(mSDropDateTime, DateUtil.SERVER_DATE, "EEE dd MMM");
    }

    public String getFormattedDropTime() {
        if (mSDropDateTime == null || mSDropDateTime.isEmpty())
            return "Flexible";
        else
            return DateUtil.getFormatedDate(mSDropDateTime, DateUtil.SERVER_DATE, "hh:mm a");
    }

    public String getFormattedPickAddress() {
        if (mSPickAddress == null || mSPickAddress.isEmpty())
            return "Unnamed...";
        else
            return mSPickAddress;
    }

    public String getFormattedDropAddress() {
        if (mSDropAddress == null || mSDropAddress.isEmpty())
            return "Unnamed...";
        else
            return mSDropAddress;
    }
}
