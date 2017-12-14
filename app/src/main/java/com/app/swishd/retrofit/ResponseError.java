package com.app.swishd.retrofit;

import com.google.gson.annotations.SerializedName;


public class ResponseError{

	@SerializedName("aErrors")
	private Object aErrors;

	@SerializedName(value = "error", alternate = {"message"})
	private String error;

	public void setAErrors(String aErrors){
		this.aErrors = aErrors;
	}

	public Object getAErrors(){
		return aErrors;
	}

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}

	@Override
 	public String toString(){
		return
			"ResponseError{" +
			"aErrors = '" + aErrors + '\'' +
			",error = '" + error + '\'' +
			"}";
		}
}