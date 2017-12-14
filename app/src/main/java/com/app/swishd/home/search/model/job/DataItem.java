package com.app.swishd.home.search.model.job;

import java.util.List;

import com.app.swishd.utility.Utility;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("oDropLoc")
	private List<Double> oDropLoc;

	@SerializedName("sPickOfficeId")
	private String sPickOfficeId;

	@SerializedName("sPickDay")
	private String sPickDay;

	@SerializedName("sVat")
	private double sVat;

	@SerializedName("pickOffice")
	private PickOffice pickOffice;

	@SerializedName("distanceFromS")
	private double distanceFromS;

	@SerializedName("sPickAddress")
	private String sPickAddress;

	@SerializedName("dropOffice")
	private DropOffice dropOffice;

	@SerializedName("sHideJob")
	private List<Object> sHideJob;

	@SerializedName("eJobStatus")
	private String eJobStatus;

	@SerializedName("sDropAddress")
	private String sDropAddress;

	@SerializedName("jobSize")
	private JobSize jobSize;

	@SerializedName("dUpdatedDate")
	private String dUpdatedDate;

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

	@SerializedName("eStatus")
	private String eStatus;

	@SerializedName("sPickDateTime")
	private String sPickDateTime;

	@SerializedName("sDropDateTime")
	private String sDropDateTime;

	@SerializedName("sRecommendedPrice")
	private double sRecommendedPrice;

	public String getsPickDateTime() {
		return sPickDateTime;
	}

	public void setsPickDateTime(String sPickDateTime) {
		this.sPickDateTime = sPickDateTime;
	}

	public String getsDropDateTime() {
		return sDropDateTime;
	}

	public void setsDropDateTime(String sDropDateTime) {
		this.sDropDateTime = sDropDateTime;
	}

	public void setODropLoc(List<Double> oDropLoc){
		this.oDropLoc = oDropLoc;
	}

	public List<Double> getODropLoc(){
		return oDropLoc;
	}

	public void setSPickOfficeId(String sPickOfficeId){
		this.sPickOfficeId = sPickOfficeId;
	}

	public String getSPickOfficeId(){
		return sPickOfficeId;
	}

	public void setSPickDay(String sPickDay){
		this.sPickDay = sPickDay;
	}

	public String getSPickDay(){
		return sPickDay;
	}

	public void setSVat(int sVat){
		this.sVat = sVat;
	}

	public double getSVat(){
		return sVat;
	}

	public void setPickOffice(PickOffice pickOffice){
		this.pickOffice = pickOffice;
	}

	public PickOffice getPickOffice(){
		return pickOffice;
	}

	public void setDistanceFromS(double distanceFromS){
		this.distanceFromS = distanceFromS;
	}

	public double getDistanceFromS(){
		return distanceFromS;
	}

	public void setSPickAddress(String sPickAddress){
		this.sPickAddress = sPickAddress;
	}

	public String getSPickAddress(){
		return Utility.nullCheck(sPickAddress);
	}

	public void setDropOffice(DropOffice dropOffice){
		this.dropOffice = dropOffice;
	}

	public DropOffice getDropOffice(){
		return dropOffice;
	}

	public void setSHideJob(List<Object> sHideJob){
		this.sHideJob = sHideJob;
	}

	public List<Object> getSHideJob(){
		return sHideJob;
	}

	public void setEJobStatus(String eJobStatus){
		this.eJobStatus = eJobStatus;
	}

	public String getEJobStatus(){
		return eJobStatus;
	}

	public void setSDropAddress(String sDropAddress){
		this.sDropAddress = sDropAddress;
	}

	public String getSDropAddress(){
		return Utility.nullCheck(sDropAddress);
	}

	public void setJobSize(JobSize jobSize){
		this.jobSize = jobSize;
	}

	public JobSize getJobSize(){
		return jobSize;
	}

	public void setDUpdatedDate(String dUpdatedDate){
		this.dUpdatedDate = dUpdatedDate;
	}

	public String getDUpdatedDate(){
		return dUpdatedDate;
	}

	public void setSRewardPrice(int sRewardPrice){
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

	public void setSInsuranceFee(int sInsuranceFee){
		this.sInsuranceFee = sInsuranceFee;
	}

	public double getSInsuranceFee(){
		return sInsuranceFee;
	}

	public void setSJobTitle(String sJobTitle){
		this.sJobTitle = sJobTitle;
	}

	public String getSJobTitle(){
		return sJobTitle;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEStatus(String eStatus){
		this.eStatus = eStatus;
	}

	public String getEStatus(){
		return eStatus;
	}

	public void setSRecommendedPrice(double sRecommendedPrice){
		this.sRecommendedPrice = sRecommendedPrice;
	}

	public double getSRecommendedPrice(){
		return sRecommendedPrice;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"oDropLoc = '" + oDropLoc + '\'' + 
			",sPickOfficeId = '" + sPickOfficeId + '\'' + 
			",sPickDay = '" + sPickDay + '\'' + 
			",sVat = '" + sVat + '\'' + 
			",pickOffice = '" + pickOffice + '\'' + 
			",distanceFromS = '" + distanceFromS + '\'' + 
			",sPickAddress = '" + sPickAddress + '\'' + 
			",dropOffice = '" + dropOffice + '\'' + 
			",sHideJob = '" + sHideJob + '\'' + 
			",eJobStatus = '" + eJobStatus + '\'' + 
			",sDropAddress = '" + sDropAddress + '\'' + 
			",jobSize = '" + jobSize + '\'' + 
			",dUpdatedDate = '" + dUpdatedDate + '\'' + 
			",sRewardPrice = '" + sRewardPrice + '\'' + 
			",oPickLoc = '" + oPickLoc + '\'' + 
			",ePickImmediately = '" + ePickImmediately + '\'' + 
			",sInsuranceFee = '" + sInsuranceFee + '\'' + 
			",sJobTitle = '" + sJobTitle + '\'' + 
			",_id = '" + id + '\'' + 
			",eStatus = '" + eStatus + '\'' + 
			",sRecommendedPrice = '" + sRecommendedPrice + '\'' + 
			"}";
		}
}