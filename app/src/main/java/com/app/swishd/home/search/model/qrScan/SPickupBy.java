package com.app.swishd.home.search.model.qrScan;

import com.google.gson.annotations.SerializedName;


public class SPickupBy{

	@SerializedName("sFirstName")
	private Object sFirstName;

	@SerializedName("sEmail")
	private Object sEmail;

	@SerializedName("sUserId")
	private String sUserId;

	@SerializedName("sMobile")
	private Object sMobile;

	@SerializedName("sLastName")
	private Object sLastName;

	@SerializedName("sSwishrEmail")
	private Object sSwishrEmail;

	@SerializedName("sSwishrUserName")
	private Object sSwishrUserName;

	public void setSFirstName(Object sFirstName){
		this.sFirstName = sFirstName;
	}

	public Object getSFirstName(){
		return sFirstName;
	}

	public void setSEmail(Object sEmail){
		this.sEmail = sEmail;
	}

	public Object getSEmail(){
		return sEmail;
	}

	public void setSUserId(String sUserId){
		this.sUserId = sUserId;
	}

	public String getSUserId(){
		return sUserId;
	}

	public void setSMobile(Object sMobile){
		this.sMobile = sMobile;
	}

	public Object getSMobile(){
		return sMobile;
	}

	public void setSLastName(Object sLastName){
		this.sLastName = sLastName;
	}

	public Object getSLastName(){
		return sLastName;
	}

	public void setSSwishrEmail(Object sSwishrEmail){
		this.sSwishrEmail = sSwishrEmail;
	}

	public Object getSSwishrEmail(){
		return sSwishrEmail;
	}

	public void setSSwishrUserName(Object sSwishrUserName){
		this.sSwishrUserName = sSwishrUserName;
	}

	public Object getSSwishrUserName(){
		return sSwishrUserName;
	}

	@Override
 	public String toString(){
		return 
			"SPickupBy{" + 
			"sFirstName = '" + sFirstName + '\'' + 
			",sEmail = '" + sEmail + '\'' + 
			",sUserId = '" + sUserId + '\'' + 
			",sMobile = '" + sMobile + '\'' + 
			",sLastName = '" + sLastName + '\'' + 
			",sSwishrEmail = '" + sSwishrEmail + '\'' + 
			",sSwishrUserName = '" + sSwishrUserName + '\'' + 
			"}";
		}
}