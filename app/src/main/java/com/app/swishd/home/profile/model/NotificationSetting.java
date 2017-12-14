
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

public class NotificationSetting {

    @SerializedName("eStatus")
    private String status;
    @SerializedName("sSettingName")
    private String settingName;
    @SerializedName("sType")
    private String type;
    @SerializedName("_id")
    private String id;

    public boolean getStatus() {
        return status != null && "true".equals(status) ? true : false;
    }

    public void setStatus(boolean status) {
        this.status = status ? "true" : "false";
    }

    public String getSettingName() {
        if ("newjob_saved_journy".equals(settingName)) {
            return "Alerts for new job";
        } else if ("wallet_payment".equals(settingName)) {
            return "Complete wallet payment";
        } else if ("swishr_offer".equals(settingName)) {
            return "Offers from swishers";
        } else if ("sendr_reject".equals(settingName)) {
            return "Rejected offer from sender";
        } else {
            return "";
        }
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getType() {
        if ("push".equals(type)) {
            return "Push Notifications";
        } else if ("message".equals(type)) {
            return "Text Message Notifications";
        } else if ("email".equals(type)) {
            return "Email Notifications";
        } else {
            return "";
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
