package com.app.swishd.home.search.model.job;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PickOffice{

	@SerializedName("sWebsite")
	private String sWebsite;

	@SerializedName("oLoc")
	private List<Double> oLoc;

	@SerializedName("sCountry")
	private String sCountry;

	@SerializedName("sZipCode")
	private String sZipCode;

	@SerializedName("oHours")
	private List<OHoursItem> oHours;

	@SerializedName("sMobile")
	private String sMobile;

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

	public void setSWebsite(String sWebsite){
		this.sWebsite = sWebsite;
	}

	public String getSWebsite(){
		return sWebsite;
	}

	public void setOLoc(List<Double> oLoc){
		this.oLoc = oLoc;
	}

	public List<Double> getOLoc(){
		return oLoc;
	}

	public void setSCountry(String sCountry){
		this.sCountry = sCountry;
	}

	public String getSCountry(){
		return sCountry;
	}

	public void setSZipCode(String sZipCode){
		this.sZipCode = sZipCode;
	}

	public String getSZipCode(){
		return sZipCode;
	}

	public void setOHours(List<OHoursItem> oHours){
		this.oHours = oHours;
	}

	public List<OHoursItem> getOHours(){
		return oHours;
	}

	public void setSMobile(String sMobile){
		this.sMobile = sMobile;
	}

	public String getSMobile(){
		return sMobile;
	}

	public void setSAddressLine2(String sAddressLine2){
		this.sAddressLine2 = sAddressLine2;
	}

	public String getSAddressLine2(){
		return sAddressLine2;
	}

	public void setSCity(String sCity){
		this.sCity = sCity;
	}

	public String getSCity(){
		return sCity;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setSAddressLine1(String sAddressLine1){
		this.sAddressLine1 = sAddressLine1;
	}

	public String getSAddressLine1(){
		return sAddressLine1;
	}

	public void setSState(String sState){
		this.sState = sState;
	}

	public String getSState(){
		return sState;
	}

	public void setSOfficeName(String sOfficeName){
		this.sOfficeName = sOfficeName;
	}

	public String getSOfficeName(){
		return sOfficeName;
	}

	@Override
 	public String toString(){
		return 
			"PickOffice{" + 
			"sWebsite = '" + sWebsite + '\'' + 
			",oLoc = '" + oLoc + '\'' + 
			",sCountry = '" + sCountry + '\'' + 
			",sZipCode = '" + sZipCode + '\'' + 
			",oHours = '" + oHours + '\'' + 
			",sMobile = '" + sMobile + '\'' + 
			",sAddressLine2 = '" + sAddressLine2 + '\'' + 
			",sCity = '" + sCity + '\'' + 
			",_id = '" + id + '\'' + 
			",sAddressLine1 = '" + sAddressLine1 + '\'' + 
			",sState = '" + sState + '\'' + 
			",sOfficeName = '" + sOfficeName + '\'' + 
			"}";
		}
}