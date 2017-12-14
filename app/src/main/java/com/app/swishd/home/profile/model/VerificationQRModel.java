
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VerificationQRModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("userProfile")
    private List<ProofData> proofDataList;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<ProofData> getProofDataList() {
        return proofDataList;
    }

    public void setProofDataList(List<ProofData> proofDataList) {
        this.proofDataList = proofDataList;
    }
}
