package com.prenticedev.prenticeapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prenticedev.prenticeapp.data.remote.response.MakeReviewResponse
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ReviewViewModel(userRepository: UserRepository) : ViewModel() {
    private val _makeReviewResponse = MutableLiveData<MakeReviewResponse>()
    val makeReviewResponse: LiveData<MakeReviewResponse> = _makeReviewResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun postReview(
        companyId: String,
        location: String,
        isRemote: Boolean,
        tags: List<String>?,
        starRating: String,
        title: String,
        description: String,
        role: String,
        startDate: String,
        endDate: String,
        oLetterUrl: String,
        annualSalary: String,
        currency: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiService = ApiConfig.getApiService()
                val response = withContext(Dispatchers.IO) {
                    apiService.createReview(
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
                        oLetterUrl,
                        annualSalary,
                        currency
                    ).execute()
                }

                if (response.isSuccessful) {
                    _makeReviewResponse.value = response.body()
                } else {
                    Log.e(TAG, response.errorBody().toString())
                }
            } catch (e: HttpException) {
                Log.e(TAG, "Failed connect to Add Review API: ${e.message().toString()}")
            } finally {
                _isLoading.value = false
            }
        }

    }

    private fun uploadOfLetterImage(){

    }

    companion object {
        private val TAG = ReviewViewModel::class.java.simpleName
    }
}