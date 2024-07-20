package com.prenticedev.prenticeapp.data.remote.retrofit

import android.content.Context
import com.prenticedev.prenticeapp.data.local.pref.UserPreference
import com.prenticedev.prenticeapp.data.local.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private lateinit var userPreference: UserPreference

    fun initialize(context: Context) {
        val dataStore = context.dataStore
        userPreference = UserPreference.getInstance(dataStore)
    }

    fun getApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = Interceptor { chain ->
            val token: String = runBlocking {
                userPreference.getToken().first()
            }
            val req = chain.request()
            val requestHeader = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeader)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
        val retrofit =Retrofit.Builder()
//            .baseUrl("http://192.168.1.109:32768/v1/")
            .baseUrl("https://prentice-jobs-webserver-dev-un47nwyyta-et.a.run.app/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}