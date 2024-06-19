package com.prenticedev.prenticeapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prenticedev.prenticeapp.data.remote.response.deployed.ReviewRequest
import com.prenticedev.prenticeapp.data.remote.response.deployed.UploadOfferResponse
import com.prenticedev.prenticeapp.data.remote.response.local_docker.MakeReviewResponse
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.data.repository.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ReviewViewModel(userRepository: UserRepository) : ViewModel() {
    private val _makeReviewResponse = MutableLiveData<MakeReviewResponse>()
    val makeReviewResponse: LiveData<MakeReviewResponse> = _makeReviewResponse

//    private val _uploadOfferUrlResponse = MutableLiveData<UploadOfferResponse>()
//    val uploadOfferResponse: LiveData<UploadOfferResponse> = _uploadOfferUrlResponse

    private val _uploadedImageUrl = MutableLiveData<String>()
    val uploadedImageUrl: LiveData<String> = _uploadedImageUrl

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun postReview(
        companyId: String,
        location: String,
        isRemote: Boolean,
        tags: List<String>?,
        starRating: Float,
        title: String,
        description: String,
        role: String,
        startDate: String,
        endDate: String,
        offerLetterUrl: String,
        annualSalary: String,
        currency: String
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val apiService = ApiConfig.getApiService()
                val client = apiService.createReview(
                    ReviewRequest(
                        companyId,
                        location,
                        isRemote,
                        tags,
                        starRating,
                        title,
                        description,
                        role,
                        startDate,
                        endDate,
                        offerLetterUrl,
                        annualSalary,
                        currency
                    )
                )

                client.enqueue(object : Callback<MakeReviewResponse> {
                    override fun onResponse(
                        call: Call<MakeReviewResponse>,
                        response: Response<MakeReviewResponse>
                    ) {
                        _makeReviewResponse.value = response.body()
                    }

                    override fun onFailure(call: Call<MakeReviewResponse>, t: Throwable) {
                        Log.e(TAG, "Failed to make an postreview api request! ${t.message}")
                    }
                })
            } catch (e: Exception) {
                Log.e(TAG, "PostReview method catches exceptions: ${e.message.toString()}")
            } finally {
                _isLoading.value = false
            }
        }

    }

    fun uploadOfferLetterImage(file: File) {

        viewModelScope.launch {
            try {
                val requestImageFile = file.asRequestBody("image/jpg".toMediaType())
                val multipart = MultipartBody.Part.createFormData(
                    "file",
                    file.name,
                    requestImageFile
                )
                _isLoading.value = true
                val apiService = ApiConfig.getApiService()
                val response = apiService.uploadOfferLetter(multipart)
                response.enqueue(object : Callback<UploadOfferResponse> {
                    override fun onResponse(
                        call: Call<UploadOfferResponse>,
                        response: Response<UploadOfferResponse>
                    ) {
                        _uploadedImageUrl.postValue(response.body()?.data.toString())
                    }

                    override fun onFailure(call: Call<UploadOfferResponse>, t: Throwable) {
                        Log.e(
                            TAG,
                            "Failed to call upload offer letter api: ${t.message.toString()}"
                        )
                    }
                })
            } catch (e: Exception) {
                Log.e(TAG, "uploadLetterImage method has exception! ${e.message.toString()}")
            } finally {
                _isLoading.value = false
            }
        }

    }


    companion object {
        private val TAG = ReviewViewModel::class.java.simpleName
    }
}