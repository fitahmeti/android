package com.app.swishd.login.model;


import com.google.gson.annotations.SerializedName;


public class ResponseSuccess{

	@SerializedName("message")
	private String message;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponseSuccess{" + 
			"message = '" + message + '\'' + 
			"}";
		}
}