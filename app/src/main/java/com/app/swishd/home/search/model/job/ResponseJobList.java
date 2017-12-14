package com.app.swishd.home.search.model.job;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseJobList{

	@SerializedName("total")
	private int total;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("message")
	private String message;

	@SerializedName("search_id")
	private String search_Id;

	@SerializedName("filter_status")
	private String filter_status;

	public String getFilter_status() {
		return filter_status;
	}

	public void setFilter_status(String filter_status) {
		this.filter_status = filter_status;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public String getSearch_Id() {
		return search_Id;
	}

	public void setSearch_Id(String search_Id) {
		this.search_Id = search_Id;
	}

	@Override
 	public String toString(){
		return 
			"ResponseJobList{" + 
			"total = '" + total + '\'' + 
			",data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}