package com.app.swishd.home.search.model.qrScan;

import java.util.List;

import com.app.swishd.home.search.model.job.DropOffice;
import com.app.swishd.home.search.model.job.PickOffice;
import com.google.gson.annotations.SerializedName;

public class Job{

	@SerializedName("oDropLoc")
	private List<Double> oDropLoc;

	@SerializedName("sVat")
	private double sVat;

	@SerializedName("jobRequestCount")
	private int jobRequestCount;

	@SerializedName("qrcode")
	private List<QrcodeItem> qrcode;

	@SerializedName("sServiceTax")
	private double sServiceTax;

	@SerializedName("sPickDateTime")
	private String sPickDateTime;

	@SerializedName("sSwishrId")
	private String sSwishrId;

	@SerializedName("sPickupBy")
	private SPickupBy sPickupBy;

	@SerializedName("sDropAddress")
	private String sDropAddress;

	@SerializedName("eJobStatus")
	private String eJobStatus;

	@SerializedName("jobSize")
	private JobSize jobSize;

	@SerializedName("jobRequested")
	private double jobRequested;

	@SerializedName("sRewardPrice")
	private double sRewardPrice;

	@SerializedName("oPickLoc")
	private List<Double> oPickLoc;

	@SerializedName("ePickImmediately")
	private String ePickImmediately;

	@SerializedName("sJobTitle")
	private String sJobTitle;

	@SerializedName("offers")
	private Offers offers;

	@SerializedName("swishr")
	private Swishr swishr;

	@SerializedName("pickOffice")
	private PickOffice pickOffice;

	@SerializedName("sPickAddress")
	private String sPickAddress;

	@SerializedName("dropOffice")
	private DropOffice dropOffice;

	@SerializedName("sPriceValue")
	private double sPriceValue;

	@SerializedName("sender")
	private Sender sender;

	@SerializedName("sInsuranceFee")
	private double sInsuranceFee;

	@SerializedName("_id")
	private String id;

	@SerializedName("sRecievedBy")
	private SRecievedBy sRecievedBy;

	public void setODropLoc(List<Double> oDropLoc){
		this.oDropLoc = oDropLoc;
	}

	public List<Double> getODropLoc(){
		return oDropLoc;
	}

	public void setSVat(double sVat){
		this.sVat = sVat;
	}

	public double getSVat(){
		return sVat;
	}

	public void setJobRequestCount(int jobRequestCount){
		this.jobRequestCount = jobRequestCount;
	}

	public int getJobRequestCount(){
		return jobRequestCount;
	}

	public void setQrcode(List<QrcodeItem> qrcode){
		this.qrcode = qrcode;
	}

	public List<QrcodeItem> getQrcode(){
		return qrcode;
	}

	public void setSServiceTax(double sServiceTax){
		this.sServiceTax = sServiceTax;
	}

	public double getSServiceTax(){
		return sServiceTax;
	}

	public void setSPickDateTime(String sPickDateTime){
		this.sPickDateTime = sPickDateTime;
	}

	public String getSPickDateTime(){
		return sPickDateTime;
	}

	public void setSSwishrId(String sSwishrId){
		this.sSwishrId = sSwishrId;
	}

	public String getSSwishrId(){
		return sSwishrId;
	}

	public void setSPickupBy(SPickupBy sPickupBy){
		this.sPickupBy = sPickupBy;
	}

	public SPickupBy getSPickupBy(){
		return sPickupBy;
	}

	public void setSDropAddress(String sDropAddress){
		this.sDropAddress = sDropAddress;
	}

	public String getSDropAddress(){
		return sDropAddress;
	}

	public void setEJobStatus(String eJobStatus){
		this.eJobStatus = eJobStatus;
	}

	public String getEJobStatus(){
		return eJobStatus;
	}

	public void setJobSize(JobSize jobSize){
		this.jobSize = jobSize;
	}

	public JobSize getJobSize(){
		return jobSize;
	}

	public void setJobRequested(double jobRequested){
		this.jobRequested = jobRequested;
	}

	public double getJobRequested(){
		return jobRequested;
	}

	public void setSRewardPrice(double sRewardPrice){
		this.sRewardPrice = sRewardPrice;
	}

	public double getSRewardPrice(){
		return sRewardPrice;
	}

	public void setOPickLoc(List<Double> oPickLoc){
		this.oPickLoc = oPickLoc;
	}

	public List<Double> getOPickLoc(){
		return oPickLoc;
	}

	public void setEPickImmediately(String ePickImmediately){
		this.ePickImmediately = ePickImmediately;
	}

	public String getEPickImmediately(){
		return ePickImmediately;
	}

	public void setSJobTitle(String sJobTitle){
		this.sJobTitle = sJobTitle;
	}

	public String getSJobTitle(){
		return sJobTitle;
	}

	public void setOffers(Offers offers){
		this.offers = offers;
	}

	public Offers getOffers(){
		return offers;
	}

	public void setSwishr(Swishr swishr){
		this.swishr = swishr;
	}

	public Swishr getSwishr(){
		return swishr;
	}

	public void setPickOffice(PickOffice pickOffice){
		this.pickOffice = pickOffice;
	}

	public PickOffice getPickOffice(){
		return pickOffice;
	}

	public void setSPickAddress(String sPickAddress){
		this.sPickAddress = sPickAddress;
	}

	public String getSPickAddress(){
		return sPickAddress;
	}

	public void setDropOffice(DropOffice dropOffice){
		this.dropOffice = dropOffice;
	}

	public DropOffice getDropOffice(){
		return dropOffice;
	}

	public void setSPriceValue(double sPriceValue){
		this.sPriceValue = sPriceValue;
	}

	public double getSPriceValue(){
		return sPriceValue;
	}

	public void setSender(Sender sender){
		this.sender = sender;
	}

	public Sender getSender(){
		return sender;
	}

	public void setSInsuranceFee(double sInsuranceFee){
		this.sInsuranceFee = sInsuranceFee;
	}

	public double getSInsuranceFee(){
		return sInsuranceFee;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setSRecievedBy(SRecievedBy sRecievedBy){
		this.sRecievedBy = sRecievedBy;
	}

	public SRecievedBy getSRecievedBy(){
		return sRecievedBy;
	}

	@Override
 	public String toString(){
		return 
			"Job{" + 
			"oDropLoc = '" + oDropLoc + '\'' + 
			",sVat = '" + sVat + '\'' + 
			",jobRequestCount = '" + jobRequestCount + '\'' + 
			",qrcode = '" + qrcode + '\'' + 
			",sServiceTax = '" + sServiceTax + '\'' + 
			",sPickDateTime = '" + sPickDateTime + '\'' + 
			",sSwishrId = '" + sSwishrId + '\'' + 
			",sPickupBy = '" + sPickupBy + '\'' + 
			",sDropAddress = '" + sDropAddress + '\'' + 
			",eJobStatus = '" + eJobStatus + '\'' + 
			",jobSize = '" + jobSize + '\'' + 
			",jobRequested = '" + jobRequested + '\'' + 
			",sRewardPrice = '" + sRewardPrice + '\'' + 
			",oPickLoc = '" + oPickLoc + '\'' + 
			",ePickImmediately = '" + ePickImmediately + '\'' + 
			",sJobTitle = '" + sJobTitle + '\'' + 
			",offers = '" + offers + '\'' + 
			",swishr = '" + swishr + '\'' + 
			",pickOffice = '" + pickOffice + '\'' + 
			",sPickAddress = '" + sPickAddress + '\'' + 
			",dropOffice = '" + dropOffice + '\'' + 
			",sPriceValue = '" + sPriceValue + '\'' + 
			",sender = '" + sender + '\'' + 
			",sInsuranceFee = '" + sInsuranceFee + '\'' + 
			",_id = '" + id + '\'' + 
			",sRecievedBy = '" + sRecievedBy + '\'' + 
			"}";
		}
}