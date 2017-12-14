
package com.app.swishd.home.profile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobListModel {

    @SerializedName("data")
    private List<Job> jobs;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("totalCount")
    private int mTotalCount;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(int totalCount) {
        mTotalCount = totalCount;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
