package com.app.swishd.home.search.model.searchDetails;

import com.google.gson.annotations.SerializedName;

public class ResponseSearchDetail{

	@SerializedName("usersearch")
	private Usersearch usersearch;

	@SerializedName("message")
	private String message;

	public void setUsersearch(Usersearch usersearch){
		this.usersearch = usersearch;
	}

	public Usersearch getUsersearch(){
		return usersearch;
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
			"ResponseSearchDetail{" + 
			"usersearch = '" + usersearch + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}