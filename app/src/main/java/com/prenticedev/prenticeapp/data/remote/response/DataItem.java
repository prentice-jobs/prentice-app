package com.prenticedev.prenticeapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("role")
	private String role;

	@SerializedName("reviewDate")
	private String reviewDate;

	@SerializedName("reviewContent")
	private String reviewContent;

	@SerializedName("companyName")
	private String companyName;

	@SerializedName("location")
	private String location;

	@SerializedName("type")
	private String type;

	@SerializedName("reviewTitle")
	private String reviewTitle;

	public String getRole(){
		return role;
	}

	public String getReviewDate(){
		return reviewDate;
	}

	public String getReviewContent(){
		return reviewContent;
	}

	public String getCompanyName(){
		return companyName;
	}

	public String getLocation(){
		return location;
	}

	public String getType(){
		return type;
	}

	public String getReviewTitle(){
		return reviewTitle;
	}
}