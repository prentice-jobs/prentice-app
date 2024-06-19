package com.prenticedev.prenticeapp.data.remote.response.deployed

import com.google.gson.annotations.SerializedName

data class UploadOfferResponse(

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: Any? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class ReviewRequest(
	@SerializedName("company_id") val companyId: String,
	@SerializedName("location") val location: String,
	@SerializedName("is_remote") val isRemote: Boolean,
	@SerializedName("tags") val tags: List<String>?,
	@SerializedName("star_rating") val starRating: Float,
	@SerializedName("title") val title: String,
	@SerializedName("description") val description: String,
	@SerializedName("role") val role: String,
	@SerializedName("start_date") val startDate: String,
	@SerializedName("end_date") val endDate: String,
	@SerializedName("offer_letter_url") val offerLetterUrl: String,
	@SerializedName("annual_salary") val annualSalary: String,
@SerializedName("salary_currency") val currency: String
)
