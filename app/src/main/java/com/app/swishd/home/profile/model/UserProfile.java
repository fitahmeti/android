
package com.app.swishd.home.profile.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class UserProfile {

    @SerializedName("complete_post_count")
    private double mCompletePostCount;
    @SerializedName("complete_swishd_count")
    private double mCompleteSwishdCount;
    @SerializedName("complete_swishd_percentage")
    private double mCompleteSwishdPercentage;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("email_verify")
    private double mEmailVerify;
    @SerializedName("facebook_verify")
    private double mFacebookVerify;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("google_verify")
    private double mGoogleVerify;
    @SerializedName("isLoginWithSocial")
    private double mIsLoginWithSocial;
    @SerializedName("join_date")
    private String mJoinDate;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("linkedin_verify")
    private double mLinkedinVerify;
    @SerializedName("mobile_verify")
    private double mMobileVerify;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("countryCode")
    private String countryCode;
    @SerializedName("post_percentage")
    private double mPostPercentage;
    @SerializedName("proofData")
    private ProofData mProofData;
    @SerializedName("proof_verify")
    private double mProofVerify;
    @SerializedName("total_wallet_amount")
    private double mTotalWalletAmount;
    @SerializedName("username")
    private String mUsername;
    @SerializedName("_id")
    private String m_id;
    @SerializedName("profile_image")
    private String profileImage;
    @SerializedName("stripe_customer_id")
    private String stripeCustomerId;

    public double getCompletePostCount() {
        return mCompletePostCount;
    }

    public void setCompletePostCount(Long completePostCount) {
        mCompletePostCount = completePostCount;
    }

    public double getCompleteSwishdCount() {
        return mCompleteSwishdCount;
    }

    public void setCompleteSwishdCount(Long completeSwishdCount) {
        mCompleteSwishdCount = completeSwishdCount;
    }

    public double getCompleteSwishdPercentage() {
        return mCompleteSwishdPercentage;
    }

    public void setCompleteSwishdPercentage(Long completeSwishdPercentage) {
        mCompleteSwishdPercentage = completeSwishdPercentage;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public double getEmailVerify() {
        return mEmailVerify;
    }

    public void setEmailVerify(Long emailVerify) {
        mEmailVerify = emailVerify;
    }

    public double getFacebookVerify() {
        return mFacebookVerify;
    }

    public void setFacebookVerify(Long facebookVerify) {
        mFacebookVerify = facebookVerify;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public double getGoogleVerify() {
        return mGoogleVerify;
    }

    public void setGoogleVerify(Long googleVerify) {
        mGoogleVerify = googleVerify;
    }

    public double getIsLoginWithSocial() {
        return mIsLoginWithSocial;
    }

    public void setIsLoginWithSocial(Long isLoginWithSocial) {
        mIsLoginWithSocial = isLoginWithSocial;
    }

    public String getJoinDate() {
        return mJoinDate;
    }

    public void setJoinDate(String joinDate) {
        mJoinDate = joinDate;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public double getLinkedinVerify() {
        return mLinkedinVerify;
    }

    public void setLinkedinVerify(Long linkedinVerify) {
        mLinkedinVerify = linkedinVerify;
    }

    public double getMobileVerify() {
        return mMobileVerify;
    }

    public void setMobileVerify(Long mobileVerify) {
        mMobileVerify = mobileVerify;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public double getPostPercentage() {
        return mPostPercentage;
    }

    public void setPostPercentage(Long postPercentage) {
        mPostPercentage = postPercentage;
    }

    public ProofData getProofData() {
        return mProofData;
    }

    public void setProofData(ProofData proofData) {
        mProofData = proofData;
    }

    public double getProofVerify() {
        return mProofVerify;
    }

    public void setProofVerify(Long proofVerify) {
        mProofVerify = proofVerify;
    }

    public double getTotalWalletAmount() {
        return mTotalWalletAmount;
    }

    public void setTotalWalletAmount(Long totalWalletAmount) {
        mTotalWalletAmount = totalWalletAmount;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static UserProfile fromString(String stringData) {
        if (stringData == null || stringData.isEmpty())
            return null;
        return new Gson().fromJson(stringData, UserProfile.class);
    }

    public int getIntWalletAmount() {
        return (int) mTotalWalletAmount;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }
}
