package com.app.swishd.home.search.model.job;


import com.google.gson.annotations.SerializedName;


public class OHoursItem{

	@SerializedName("_id")
	private String id;

	@SerializedName("close")
	private int close;

	@SerializedName("day")
	private int day;

	@SerializedName("open")
	private int open;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setClose(int close){
		this.close = close;
	}

	public int getClose(){
		return close;
	}

	public void setDay(int day){
		this.day = day;
	}

	public int getDay(){
		return day;
	}

	public void setOpen(int open){
		this.open = open;
	}

	public int getOpen(){
		return open;
	}

	@Override
 	public String toString(){
		return 
			"OHoursItem{" + 
			"_id = '" + id + '\'' + 
			",close = '" + close + '\'' + 
			",day = '" + day + '\'' + 
			",open = '" + open + '\'' + 
			"}";
		}
}