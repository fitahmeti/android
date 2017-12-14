package com.app.swishd.login.model;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin{

	@SerializedName("Authorization")
	private String authorization;

	@SerializedName("message")
	private String message;

	public void setAuthorization(String authorization){
		this.authorization = authorization;
	}

	public String getAuthorization(){
		return authorization;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponseLogin{" + 
			"authorization = '" + authorization + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}