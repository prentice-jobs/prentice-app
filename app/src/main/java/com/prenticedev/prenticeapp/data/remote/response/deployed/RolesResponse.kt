package com.prenticedev.prenticeapp.data.remote.response.deployed

import com.google.gson.annotations.SerializedName

data class RolesResponse(

	@field:SerializedName("data")
	val data: List<String?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: Any? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
