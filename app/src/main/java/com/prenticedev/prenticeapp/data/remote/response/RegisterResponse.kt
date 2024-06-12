package com.prenticedev.prenticeapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: Any? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class Data(

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)

//REQUEST DATA CLASS
data class EmailRequest(val email: String)
data class RegisterRequest(
    @SerializedName("firebase_uid") val firebaseUID: String,
    @SerializedName("email") val email: String,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("photo_url") val photoUrl: String,
    @SerializedName("email_verified") val emailVerified: String
)