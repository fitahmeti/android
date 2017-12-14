package com.app.swishd.home.search.model.JobDetails;

import com.google.gson.annotations.SerializedName;

public class QRCode{

	@SerializedName("sScanImage")
	private String sScanImage;

	@SerializedName("sScanCode")
	private String sScanCode;

	@SerializedName("sType")
	private String sType;

	@SerializedName("dCreatedDate")
	private String dCreatedDate;

	@SerializedName("_id")
	private String id;

	public void setSScanImage(String sScanImage){
		this.sScanImage = sScanImage;
	}

	public String getSScanImage(){
		return sScanImage;
	}

	public void setSScanCode(String sScanCode){
		this.sScanCode = sScanCode;
	}

	public String getSScanCode(){
		return sScanCode;
	}

	public void setSType(String sType){
		this.sType = sType;
	}

	public String getSType(){
		return sType;
	}

	public void setDCreatedDate(String dCreatedDate){
		this.dCreatedDate = dCreatedDate;
	}

	public String getDCreatedDate(){
		return dCreatedDate;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"QRCode{" + 
			"sScanImage = '" + sScanImage + '\'' + 
			",sScanCode = '" + sScanCode + '\'' + 
			",sType = '" + sType + '\'' + 
			",dCreatedDate = '" + dCreatedDate + '\'' + 
			",_id = '" + id + '\'' + 
			"}";
		}
}