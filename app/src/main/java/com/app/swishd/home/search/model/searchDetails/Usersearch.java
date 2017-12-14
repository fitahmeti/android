package com.app.swishd.home.search.model.searchDetails;

import java.util.List;

import com.app.swishd.home.search.model.Destination;
import com.app.swishd.home.search.model.Source;
import com.google.gson.annotations.SerializedName;

public class Usersearch{

	@SerializedName("destination_address")
	private String destinationAddress;

	@SerializedName("destination")
	private Destination destination;

	@SerializedName("anytime")
	private String anytime;

	@SerializedName("source")
	private Source source;

	@SerializedName("filter_status")
	private String filterStatus;

	@SerializedName("everyday")
	private List<Object> everyday;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("source_address")
	private String sourceAddress;

	@SerializedName("__v")
	private int V;

	@SerializedName("_id")
	private String id;

	@SerializedName("updated_date")
	private String updatedDate;

	@SerializedName("created_date")
	private String createdDate;

	@SerializedName("swishdoffice")
	private String swishdoffice;

	@SerializedName("specific_date")
	private Object specificDate;

	@SerializedName("filter_counter")
	private int filterCounter;

	public void setDestinationAddress(String destinationAddress){
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationAddress(){
		return destinationAddress;
	}

	public void setDestination(Destination destination){
		this.destination = destination;
	}

	public Destination getDestination(){
		return destination;
	}

	public void setAnytime(String anytime){
		this.anytime = anytime;
	}

	public String getAnytime(){
		return anytime;
	}

	public void setSource(Source source){
		this.source = source;
	}

	public Source getSource(){
		return source;
	}

	public void setFilterStatus(String filterStatus){
		this.filterStatus = filterStatus;
	}

	public String getFilterStatus(){
		return filterStatus;
	}

	public void setEveryday(List<Object> everyday){
		this.everyday = everyday;
	}

	public List<Object> getEveryday(){
		return everyday;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setSourceAddress(String sourceAddress){
		this.sourceAddress = sourceAddress;
	}

	public String getSourceAddress(){
		return sourceAddress;
	}

	public void setV(int V){
		this.V = V;
	}

	public int getV(){
		return V;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setUpdatedDate(String updatedDate){
		this.updatedDate = updatedDate;
	}

	public String getUpdatedDate(){
		return updatedDate;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setSwishdoffice(String swishdoffice){
		this.swishdoffice = swishdoffice;
	}

	public String getSwishdoffice(){
		return swishdoffice;
	}

	public void setSpecificDate(Object specificDate){
		this.specificDate = specificDate;
	}

	public Object getSpecificDate(){
		return specificDate;
	}

	public void setFilterCounter(int filterCounter){
		this.filterCounter = filterCounter;
	}

	public int getFilterCounter(){
		return filterCounter;
	}

	@Override
 	public String toString(){
		return 
			"Usersearch{" + 
			"destination_address = '" + destinationAddress + '\'' + 
			",destination = '" + destination + '\'' + 
			",anytime = '" + anytime + '\'' + 
			",source = '" + source + '\'' + 
			",filter_status = '" + filterStatus + '\'' + 
			",everyday = '" + everyday + '\'' + 
			",user_id = '" + userId + '\'' + 
			",source_address = '" + sourceAddress + '\'' + 
			",__v = '" + V + '\'' + 
			",_id = '" + id + '\'' + 
			",updated_date = '" + updatedDate + '\'' + 
			",created_date = '" + createdDate + '\'' + 
			",swishdoffice = '" + swishdoffice + '\'' + 
			",specific_date = '" + specificDate + '\'' + 
			",filter_counter = '" + filterCounter + '\'' + 
			"}";
		}
}