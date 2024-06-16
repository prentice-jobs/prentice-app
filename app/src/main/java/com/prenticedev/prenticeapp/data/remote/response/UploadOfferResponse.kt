package com.prenticedev.prenticeapp.data.remote.response

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
