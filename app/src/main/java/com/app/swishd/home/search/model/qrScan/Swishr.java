package com.app.swishd.home.search.model.qrScan;


import com.google.gson.annotations.SerializedName;


public class Swishr{

	@SerializedName("profile_image")
	private String profileImage;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("linkedin_id")
	private String linkedinId;

	@SerializedName("_id")
	private String id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public void setProfileImage(String profileImage){
		this.profileImage = profileImage;
	}

	public String getProfileImage(){
		return profileImage;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setLinkedinId(String linkedinId){
		this.linkedinId = linkedinId;
	}

	public String getLinkedinId(){
		return linkedinId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"Swishr{" + 
			"profile_image = '" + profileImage + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",linkedin_id = '" + linkedinId + '\'' + 
			",_id = '" + id + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}