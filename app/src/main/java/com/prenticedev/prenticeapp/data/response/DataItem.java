package com.prenticedev.prenticeapp.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("role")
	private String role;

	@SerializedName("date_posted")
	private String datePosted;

	@SerializedName("logo_url")
	private String logoUrl;

	@SerializedName("annual_salary")
	private int annualSalary;

	@SerializedName("company")
	private String company;

	@SerializedName("company_rating")
	private double companyRating;

	@SerializedName("company_reviews")
	private List<CompanyReviewsItem> companyReviews;

	public String getRole(){
		return role;
	}

	public String getDatePosted(){
		return datePosted;
	}

	public String getLogoUrl(){
		return logoUrl;
	}

	public int getAnnualSalary(){
		return annualSalary;
	}

	public String getCompany(){
		return company;
	}

	public double getCompanyRating(){
		return companyRating;
	}

	public List<CompanyReviewsItem> getCompanyReviews(){
		return companyReviews;
	}
}