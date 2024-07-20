package com.prenticedev.prenticeapp.data.remote.response.local_docker

import com.google.gson.annotations.SerializedName

data class MakeReviewResponse(

    @field:SerializedName("data")
	val data: MakeReviewData? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("error")
	val error: Any? = null,

    @field:SerializedName("status")
	val status: Int? = null
)

data class MakeReviewData(

	@field:SerializedName("company_id")
	val companyId: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("author_id")
	val authorId: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
