package com.prenticedev.prenticeapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailReviewResponse(

	@field:SerializedName("data")
	val data: DetailReviewData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: Any? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DetailReviewData(

	@field:SerializedName("comments")
	val comments: List<Any?>? = null,

	@field:SerializedName("review")
	val review: Review? = null
)

data class Review(

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("offer_letter_url")
	val offerLetterUrl: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("company_id")
	val companyId: String? = null,

	@field:SerializedName("is_remote")
	val isRemote: Boolean? = null,

	@field:SerializedName("salary_currency")
	val salaryCurrency: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("tags")
	val tags: String? = null,

	@field:SerializedName("is_deleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("star_rating")
	val starRating: Int? = null,

	@field:SerializedName("annual_salary")
	val annualSalary: Int? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("author_id")
	val authorId: String? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null
)
