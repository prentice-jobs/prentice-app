package com.prenticedev.prenticeapp.data.remote.retrofit

import com.prenticedev.prenticeapp.data.remote.response.deployed.CompanyDeployedResponse
import com.prenticedev.prenticeapp.data.remote.response.deployed.CompanyResponseItem
import com.prenticedev.prenticeapp.data.remote.response.deployed.DetailCompanyResponseDeployed
import com.prenticedev.prenticeapp.data.remote.response.deployed.EmailRequest
import com.prenticedev.prenticeapp.data.remote.response.deployed.RegisterRequest
import com.prenticedev.prenticeapp.data.remote.response.deployed.RegisterResponse
import com.prenticedev.prenticeapp.data.remote.response.deployed.ReviewFeedResponse
import com.prenticedev.prenticeapp.data.remote.response.deployed.ReviewRequest
import com.prenticedev.prenticeapp.data.remote.response.deployed.UploadOfferResponse
import com.prenticedev.prenticeapp.data.remote.response.local_docker.MakeReviewResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    // -----------------------------------------------------------------------
    //    COMPANIES API
//    @GET("company/all")
//    fun getCompanies(                     // GET COMPANIES API FOR LOCAL PROJECTS
//    ): Call<List<CompanyResponseItem>>

    @GET("company/all")
    fun getCompanies(): Call<CompanyDeployedResponse>

    @GET("company/search-name")
    fun searchCompany(
        @Query("name") companyName: String
    ): Call<CompanyDeployedResponse>

    @GET("company/{id}")
    fun getDetailCompany(
        @Path("id") id: String
    ): Call<DetailCompanyResponseDeployed>

    //  ---------------------------------------------------------------------
//    REVIEW API

    @GET("review/feed")
    fun getReviewFeed(): Call<ReviewFeedResponse>


    @POST("review/")
    suspend fun createReview(
        @Body reviewRequest: ReviewRequest
    ): Call<MakeReviewResponse>

    @Multipart
    @POST("review/offer")
     fun uploadOfferLetter(
        @Part file: MultipartBody.Part
    ): Call<UploadOfferResponse>

    @GET("review/{reviewId}")
    fun getReviewDetail(
        @Path("reviewId") reviewId: String
    ): Call<CompanyResponseItem>
}
