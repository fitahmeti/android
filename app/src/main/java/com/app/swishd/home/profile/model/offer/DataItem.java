package com.app.swishd.home.profile.model.offer;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("dProposeDateTime")
	private String dProposeDateTime;

	@SerializedName("facebook_verify")
	private int facebookVerify;

	@SerializedName("sUserId")
	private String sUserId;

	@SerializedName("google_verify")
	private int googleVerify;

	@SerializedName("cancel_job_percentage")
	private int cancelJobPercentage;

	@SerializedName("proof_verify")
	private int proofVerify;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("eOfferStatus")
	private String eOfferStatus;

	@SerializedName("dOfferDateTime")
	private String dOfferDateTime;

	@SerializedName("profile_image")
	private String profileImage;

	@SerializedName("fb_friends")
	private int fbFriends;

	@SerializedName("mobile_verify")
	private int mobileVerify;

	@SerializedName("ln_friends")
	private int lnFriends;

	@SerializedName("email_verify")
	private int emailVerify;

	@SerializedName("linkedin_verify")
	private int linkedinVerify;

	@SerializedName("late_job_percentage")
	private int lateJobPercentage;

	@SerializedName("_id")
	private String id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("username")
	private String username;

	@SerializedName("complete_swishd_percentage")
	private int completeSwishdPercentage;

	public void setDProposeDateTime(String dProposeDateTime){
		this.dProposeDateTime = dProposeDateTime;
	}

	public String getDProposeDateTime(){
		return dProposeDateTime;
	}

	public void setFacebookVerify(int facebookVerify){
		this.facebookVerify = facebookVerify;
	}

	public int getFacebookVerify(){
		return facebookVerify;
	}

	public void setSUserId(String sUserId){
		this.sUserId = sUserId;
	}

	public String getSUserId(){
		return sUserId;
	}

	public void setGoogleVerify(int googleVerify){
		this.googleVerify = googleVerify;
	}

	public int getGoogleVerify(){
		return googleVerify;
	}

	public void setCancelJobPercentage(int cancelJobPercentage){
		this.cancelJobPercentage = cancelJobPercentage;
	}

	public int getCancelJobPercentage(){
		return cancelJobPercentage;
	}

	public void setProofVerify(int proofVerify){
		this.proofVerify = proofVerify;
	}

	public int getProofVerify(){
		return proofVerify;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setEOfferStatus(String eOfferStatus){
		this.eOfferStatus = eOfferStatus;
	}

	public String getEOfferStatus(){
		return eOfferStatus;
	}

	public void setDOfferDateTime(String dOfferDateTime){
		this.dOfferDateTime = dOfferDateTime;
	}

	public String getDOfferDateTime(){
		return dOfferDateTime;
	}

	public void setProfileImage(String profileImage){
		this.profileImage = profileImage;
	}

	public String getProfileImage(){
		return profileImage;
	}

	public void setFbFriends(int fbFriends){
		this.fbFriends = fbFriends;
	}

	public int getFbFriends(){
		return fbFriends;
	}

	public void setMobileVerify(int mobileVerify){
		this.mobileVerify = mobileVerify;
	}

	public int getMobileVerify(){
		return mobileVerify;
	}

	public void setLnFriends(int lnFriends){
		this.lnFriends = lnFriends;
	}

	public int getLnFriends(){
		return lnFriends;
	}

	public void setEmailVerify(int emailVerify){
		this.emailVerify = emailVerify;
	}

	public int getEmailVerify(){
		return emailVerify;
	}

	public void setLinkedinVerify(int linkedinVerify){
		this.linkedinVerify = linkedinVerify;
	}

	public int getLinkedinVerify(){
		return linkedinVerify;
	}

	public void setLateJobPercentage(int lateJobPercentage){
		this.lateJobPercentage = lateJobPercentage;
	}

	public int getLateJobPercentage(){
		return lateJobPercentage;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setCompleteSwishdPercentage(int completeSwishdPercentage){
		this.completeSwishdPercentage = completeSwishdPercentage;
	}

	public int getCompleteSwishdPercentage(){
		return completeSwishdPercentage;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"dProposeDateTime = '" + dProposeDateTime + '\'' + 
			",facebook_verify = '" + facebookVerify + '\'' + 
			",sUserId = '" + sUserId + '\'' + 
			",google_verify = '" + googleVerify + '\'' + 
			",cancel_job_percentage = '" + cancelJobPercentage + '\'' + 
			",proof_verify = '" + proofVerify + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",eOfferStatus = '" + eOfferStatus + '\'' + 
			",dOfferDateTime = '" + dOfferDateTime + '\'' + 
			",profile_image = '" + profileImage + '\'' + 
			",fb_friends = '" + fbFriends + '\'' + 
			",mobile_verify = '" + mobileVerify + '\'' + 
			",ln_friends = '" + lnFriends + '\'' + 
			",email_verify = '" + emailVerify + '\'' + 
			",linkedin_verify = '" + linkedinVerify + '\'' + 
			",late_job_percentage = '" + lateJobPercentage + '\'' + 
			",_id = '" + id + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",username = '" + username + '\'' + 
			",complete_swishd_percentage = '" + completeSwishdPercentage + '\'' + 
			"}";
		}
}