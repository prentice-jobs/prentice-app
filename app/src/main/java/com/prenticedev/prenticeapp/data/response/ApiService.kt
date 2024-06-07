package com.prenticedev.prenticeapp.data.response

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("salary/review/{id}")
    fun getFeed(
        @Path("id") id: String
    ): Call<Response>
}