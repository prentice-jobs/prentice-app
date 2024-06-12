package com.prenticedev.prenticeapp.data.remote.retrofit

import com.prenticedev.prenticeapp.data.remote.response.CompanyResponseItem
import com.prenticedev.prenticeapp.data.remote.response.EmailRequest
import com.prenticedev.prenticeapp.data.remote.response.RegisterRequest
import com.prenticedev.prenticeapp.data.remote.response.RegisterResponse
import com.prenticedev.prenticeapp.data.remote.response.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    //    ACCOUNT API

    @POST("account/register")
    fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @POST("account/exists")
    fun checkUserIsExist(
        @Body email: EmailRequest
    ): Call<Boolean>

    //    COMPANIES API
    @GET("company/all")
    fun getCompanies(
    ): List<CompanyResponseItem>

    @GET("company/{id}")
    fun getDetailCompany(
        @Path("id") id: String
    ): Call<CompanyResponseItem>

    @GET("salary/review/{id}")
    fun getFeed(
        @Path("id") id: String
    ): Call<Response>
}
