package com.prenticedev.prenticeapp.data.remote.response.deployed

import com.google.gson.annotations.SerializedName

data class CompanyDeployedResponse(

	@field:SerializedName("data")
	val data: List<CompanyResponseItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: Any? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class CompanyReviewsItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class CompanyResponseItem(

	@field:SerializedName("keywords")
	val keywords: List<String?>? = null,

	@field:SerializedName("logo_url")
	val logoUrl: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("review_count")
	val reviewCount: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("company_sentiment")
	val companySentiment: Double? = null,

	@field:SerializedName("display_name")
	val displayName: String? = null,

	@field:SerializedName("company_reviews")
	val companyReviews: List<CompanyReviewsItem?>? = null,

	@field:SerializedName("tags")
	val tags: List<String?>? = null,

	@field:SerializedName("is_deleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("star_rating")
	val starRating: Double? = null,

	@field:SerializedName("id")
	val id: String? = null
)
