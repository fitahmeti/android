package com.app.swishd.home.search.model.qrScan;

import com.google.gson.annotations.SerializedName;


public class JobSize{

	@SerializedName("sSizeDescription")
	private String sSizeDescription;

	@SerializedName("sOriginalSizePicture")
	private String sOriginalSizePicture;

	@SerializedName("sSizeTitle")
	private String sSizeTitle;

	@SerializedName("_id")
	private String id;

	public void setSSizeDescription(String sSizeDescription){
		this.sSizeDescription = sSizeDescription;
	}

	public String getSSizeDescription(){
		return sSizeDescription;
	}

	public void setSOriginalSizePicture(String sOriginalSizePicture){
		this.sOriginalSizePicture = sOriginalSizePicture;
	}

	public String getSOriginalSizePicture(){
		return sOriginalSizePicture;
	}

	public void setSSizeTitle(String sSizeTitle){
		this.sSizeTitle = sSizeTitle;
	}

	public String getSSizeTitle(){
		return sSizeTitle;
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
			"JobSize{" + 
			"sSizeDescription = '" + sSizeDescription + '\'' + 
			",sOriginalSizePicture = '" + sOriginalSizePicture + '\'' + 
			",sSizeTitle = '" + sSizeTitle + '\'' + 
			",_id = '" + id + '\'' + 
			"}";
		}
}