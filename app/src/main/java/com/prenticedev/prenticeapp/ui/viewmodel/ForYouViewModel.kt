package com.prenticedev.prenticeapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prenticedev.prenticeapp.data.remote.response.deployed.ReviewFeedItems
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ForYouViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _reviewFeedResponse = MutableLiveData<List<ReviewFeedItems>>()
    val reviewFeedDataResponse: LiveData<List<ReviewFeedItems>> = _reviewFeedResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getFeedData()
    }

    fun getFeedData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val response = withContext(Dispatchers.IO) {
                    apiService.getReviewFeed().execute()
                }
                if (response.isSuccessful) {
                    _reviewFeedResponse.value = response.body()?.data?.filterNotNull()
                } else {
                    Log.e(TAG, "Failed to fetch feed data ${response.errorBody()?.toString()}")
                }
            } catch (e: HttpException) {
                Log.e(TAG, "Failed to fetch feed data ${e.message()}")

            } finally {
                _isLoading.value = false
            }

        }
    }

    companion object {
        private val TAG = ForYouViewModel::class.java.simpleName
    }
}