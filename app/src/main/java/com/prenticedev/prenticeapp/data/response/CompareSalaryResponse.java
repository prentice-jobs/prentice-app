package com.prenticedev.prenticeapp.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CompareSalaryResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("message")
	private String message;

	@SerializedName("error")
	private Object error;

	@SerializedName("status")
	private int status;

	public List<DataItem> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public Object getError(){
		return error;
	}

	public int getStatus(){
		return status;
	}
}