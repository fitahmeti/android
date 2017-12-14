package com.app.swishd.home.search.model;


import com.app.swishd.utility.Const;

import java.util.ArrayList;

public class FindJobModel {

    private int start;
    private int limit = 15;
    private double source_latitude = 52.1359729;
    private double source_longitude = -0.4666546;
    private double destination_latitude;
    private double destination_longitude;
    private String source_address;
    private String destination_address;
    private CharSequence anytime = Const.YES_NO.no.toString();
    private ArrayList<String> everyday;
    private String specific_date;
    private String vehicle;
    private CharSequence swishdoffice;
    private CharSequence sortby;
    private String search_id;
    private boolean isFav = false;

    public String getSearch_id() {
        return search_id;
    }

    public void setSearch_id(String search_id) {
        this.search_id = search_id;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public String getId() {
        return search_id;
    }

    public void setId(String id) {
        this.search_id = id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public double getSource_latitude() {
        return source_latitude;
    }

    public void setSource_latitude(double source_latitude) {
        this.source_latitude = source_latitude;
    }

    public double getSource_longitude() {
        return source_longitude;
    }

    public void setSource_longitude(double source_longitude) {
        this.source_longitude = source_longitude;
    }

    public double getDestination_latitude() {
        return destination_latitude;
    }

    public void setDestination_latitude(double destination_latitude) {
        this.destination_latitude = destination_latitude;
    }

    public double getDestination_longitude() {
        return destination_longitude;
    }

    public void setDestination_longitude(double destination_longitude) {
        this.destination_longitude = destination_longitude;
    }

    public String getSource_address() {
        return source_address;
    }

    public void setSource_address(String source_address) {
        this.source_address = source_address;
    }

    public String getDestination_address() {
        return destination_address;
    }

    public void setDestination_address(String destination_address) {
        this.destination_address = destination_address;
    }

    public CharSequence getAnytime() {
        return anytime;
    }

    public void setAnytime(CharSequence anytime) {
        this.anytime = anytime;
    }

    public ArrayList<String> getEveryday() {
        if (everyday == null)
            everyday = new ArrayList<>();
        return everyday;
    }

    public void setEveryday(ArrayList<String> everyday) {
        this.everyday = everyday;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public CharSequence getSwishdoffice() {
        return swishdoffice;
    }

    public void setSwishdoffice(CharSequence swishdoffice) {
        this.swishdoffice = swishdoffice;
    }

    public CharSequence getSortby() {
        return sortby;
    }

    public void setSortby(CharSequence sortby) {
        this.sortby = sortby;
    }

    public String getSpecific_date() {
        return specific_date;
    }

    public void setSpecific_date(String specific_date) {
        this.specific_date = specific_date;
    }

    @Override
    public String toString() {
        return "Start" + start + "  " +
                "limit" + limit + "  " +
                "source_latitude" + source_latitude + "  " +
                "source_longitude" + source_longitude + "  " +
                "destination_latitude" + destination_latitude + "  " +
                "destination_longitude" + destination_longitude + "  " +
                "source_address" + source_address + "  " +
                "destination_address" + destination_address + "  " +
                "anytime" + anytime + "  " +
                "everyday" + everyday + "  " +
                "specific_date" + specific_date + "  " +
                "vehicle" + vehicle + "  " +
                "swishdoffice" + swishdoffice + "  " +
                "sortby" + sortby + "  ";
    }
}
