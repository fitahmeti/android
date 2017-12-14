package com.app.swishd.home.search.model;

import com.app.swishd.utility.Utility;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataItem {

    @SerializedName("destination_address")
    private String destinationAddress;

    @SerializedName("source_address")
    private String sourceAddress;

    @SerializedName("destination")
    private Destination destination;

    @SerializedName("anytime")
    private String anytime;

    @SerializedName("_id")
    private String id;

    @SerializedName("source")
    private Source source;

    @SerializedName("swishdoffice")
    private String swishdoffice;

    @SerializedName("specific_date")
    private String specificDate;

    @SerializedName("filter_counter")
    private int filterCounter;

    @SerializedName("filter_status")
    private String filterStatus;

    @SerializedName("everyday")
    private List<String> everyday;

    public String getDestinationAddress() {
        return Utility.nullCheck(destinationAddress);
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getSourceAddress() {
        return Utility.nullCheck(sourceAddress);
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public String getAnytime() {
        return anytime;
    }

    public void setAnytime(String anytime) {
        this.anytime = anytime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getSwishdoffice() {
        return swishdoffice;
    }

    public void setSwishdoffice(String swishdoffice) {
        this.swishdoffice = swishdoffice;
    }

    public String getSpecificDate() {
        return specificDate;
    }

    public void setSpecificDate(String specificDate) {
        this.specificDate = specificDate;
    }

    public int getFilterCounter() {
        return filterCounter;
    }

    public void setFilterCounter(int filterCounter) {
        this.filterCounter = filterCounter;
    }

    public String getFilterStatus() {
        return filterStatus;
    }

    public void setFilterStatus(String filterStatus) {
        this.filterStatus = filterStatus;
    }

    public List<String> getEveryday() {
        return everyday;
    }

    public void setEveryday(List<String> everyday) {
        this.everyday = everyday;
    }

    @Override
    public String toString() {
        return
                "DataItem{" +
                        "destination_address = '" + destinationAddress + '\'' +
                        ",source_address = '" + sourceAddress + '\'' +
                        ",destination = '" + destination + '\'' +
                        ",anytime = '" + anytime + '\'' +
                        ",_id = '" + id + '\'' +
                        ",source = '" + source + '\'' +
                        ",swishdoffice = '" + swishdoffice + '\'' +
                        ",specific_date = '" + specificDate + '\'' +
                        ",filter_counter = '" + filterCounter + '\'' +
                        ",filter_status = '" + filterStatus + '\'' +
                        ",everyday = '" + everyday + '\'' +
                        "}";
    }
}