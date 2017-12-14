package com.app.swishd.home.search.model.qrScan;

import com.google.gson.annotations.SerializedName;

public class Offers{

	@SerializedName("dProposeDateTime")
	private Object dProposeDateTime;

	@SerializedName("dOfferDateTime")
	private String dOfferDateTime;

	@SerializedName("sUserId")
	private String sUserId;

	@SerializedName("eOfferStatus")
	private String eOfferStatus;

	@SerializedName("_id")
	private String id;

	public void setDProposeDateTime(Object dProposeDateTime){
		this.dProposeDateTime = dProposeDateTime;
	}

	public Object getDProposeDateTime(){
		return dProposeDateTime;
	}

	public void setDOfferDateTime(String dOfferDateTime){
		this.dOfferDateTime = dOfferDateTime;
	}

	public String getDOfferDateTime(){
		return dOfferDateTime;
	}

	public void setSUserId(String sUserId){
		this.sUserId = sUserId;
	}

	public String getSUserId(){
		return sUserId;
	}

	public void setEOfferStatus(String eOfferStatus){
		this.eOfferStatus = eOfferStatus;
	}

	public String getEOfferStatus(){
		return eOfferStatus;
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
			"Offers{" + 
			"dProposeDateTime = '" + dProposeDateTime + '\'' + 
			",dOfferDateTime = '" + dOfferDateTime + '\'' + 
			",sUserId = '" + sUserId + '\'' + 
			",eOfferStatus = '" + eOfferStatus + '\'' + 
			",_id = '" + id + '\'' + 
			"}";
		}
}