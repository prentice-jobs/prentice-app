package com.prenticedev.prenticeapp.data.remote.response.deployed

import com.google.gson.annotations.SerializedName

data class SetPreferenceResponse(

    @field:SerializedName("data")
    val data: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class SetPreferenceRequest(
    @SerializedName("role") val role: String,
    @SerializedName("industry") val industry: String,
    @SerializedName("location") val location: String
)
