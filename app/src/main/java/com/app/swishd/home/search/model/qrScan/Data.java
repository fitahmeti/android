package com.app.swishd.home.search.model.qrScan;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("sJobId")
	private String sJobId;

	@SerializedName("sScanCode")
	private String sScanCode;

	@SerializedName("sType")
	private String sType;

	@SerializedName("dCreatedDate")
	private String dCreatedDate;

	@SerializedName("__v")
	private int V;

	@SerializedName("_id")
	private String id;

	@SerializedName("job")
	private Job job;

	public void setSJobId(String sJobId){
		this.sJobId = sJobId;
	}

	public String getSJobId(){
		return sJobId;
	}

	public void setSScanCode(String sScanCode){
		this.sScanCode = sScanCode;
	}

	public String getSScanCode(){
		return sScanCode;
	}

	public void setSType(String sType){
		this.sType = sType;
	}

	public String getSType(){
		return sType;
	}

	public void setDCreatedDate(String dCreatedDate){
		this.dCreatedDate = dCreatedDate;
	}

	public String getDCreatedDate(){
		return dCreatedDate;
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

	public void setJob(Job job){
		this.job = job;
	}

	public Job getJob(){
		return job;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"sJobId = '" + sJobId + '\'' + 
			",sScanCode = '" + sScanCode + '\'' + 
			",sType = '" + sType + '\'' + 
			",dCreatedDate = '" + dCreatedDate + '\'' + 
			",__v = '" + V + '\'' + 
			",_id = '" + id + '\'' + 
			",job = '" + job + '\'' + 
			"}";
		}
}