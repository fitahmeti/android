
package com.app.swishd.home.profile.model;

import com.app.swishd.retrofit.call.Api;
import com.google.gson.annotations.SerializedName;

public class ProofData {

    @SerializedName("scan_code")
    private String scanCode;
    @SerializedName("verify_id_proof")
    private String idProof;
    @SerializedName("verify_address_proof")
    private String addressProof;
    @SerializedName("qr_code_image")
    private String qrImage;

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }

    public String getIdProof() {
        return Api.BASE_URL_IMAGE + idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getAddressProof() {
        return Api.BASE_URL_IMAGE + addressProof;
    }

    public void setAddressProof(String addressProof) {
        this.addressProof = addressProof;
    }

    public String getQrImage() {
        return Api.BASE_URL_IMAGE + qrImage;
    }

    public void setQrImage(String qrImage) {
        this.qrImage = qrImage;
    }
}
