package com.prenticedev.prenticeapp.data.remote.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.prenticedev.prenticeapp.data.remote.response.deployed.ReviewFeedItems;

public class Response{

	@SerializedName("data")
	private List<ReviewFeedItems> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public List<ReviewFeedItems> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}
}