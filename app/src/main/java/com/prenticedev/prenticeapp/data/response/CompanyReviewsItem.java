package com.prenticedev.prenticeapp.data.response;

import com.google.gson.annotations.SerializedName;

public class CompanyReviewsItem{

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

	public String getDescription(){
		return description;
	}

	public String getTitle(){
		return title;
	}
}