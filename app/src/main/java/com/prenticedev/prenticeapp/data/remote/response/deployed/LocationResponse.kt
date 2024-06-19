package com.prenticedev.prenticeapp.data.remote.response.deployed

import com.google.gson.annotations.SerializedName

data class LocationResponse(

	@field:SerializedName("LocationResponse")
	val locationResponse: List<LocationResponseItem?>? = null
)

data class LocationResponseItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
