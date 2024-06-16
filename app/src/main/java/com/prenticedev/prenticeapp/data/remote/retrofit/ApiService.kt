package com.prenticedev.prenticeapp.data.remote.retrofit

import com.prenticedev.prenticeapp.data.remote.response.CompanyResponseItem
import com.prenticedev.prenticeapp.data.remote.response.DetailReviewResponse
import com.prenticedev.prenticeapp.data.remote.response.EmailRequest
import com.prenticedev.prenticeapp.data.remote.response.MakeReviewResponse
import com.prenticedev.prenticeapp.data.remote.response.RegisterRequest
import com.prenticedev.prenticeapp.data.remote.response.RegisterResponse
import com.prenticedev.prenticeapp.data.remote.response.ReviewFeedResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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
    @GET("company/all")
    fun getCompanies(
    ): Call<List<CompanyResponseItem>>

    @GET("company/search-name")
    fun searchCompany(
        @Query("name") companyName: String
    ): Call<List<CompanyResponseItem>>

    @GET("company/{id}")
    fun getDetailCompany(
        @Path("id") id: String
    ): Call<CompanyResponseItem>

    //  ---------------------------------------------------------------------
//    REVIEW API

    @GET("review/feed")
    fun getReviewFeed(): Call<ReviewFeedResponse>

    @FormUrlEncoded
    @POST("review/")
    fun createReview(
        @Field("company_id") companyId: String,
        @Field("location") location: String,
        @Field("is_remote") isRemote: Boolean,
        @Field("tags") tags: List<String>?,
        @Field("star_rating") starRating: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("role") role: String,
        @Field("start_date") startDate: String,
        @Field("end_date") endDate: String,
        @Field("offer_letter_url") ofLetterUrl: String,
        @Field("annual_salary") annualSalary: String,
        @Field("salary_currency") currency: String
    ): Call<MakeReviewResponse>

    @GET("review/{reviewId}")
    fun getReviewDetail(
        @Path("reviewId") reviewId: String
    ): Call<DetailReviewResponse>
}
