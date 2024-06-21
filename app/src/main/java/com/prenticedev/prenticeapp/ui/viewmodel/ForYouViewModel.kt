package com.prenticedev.prenticeapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prenticedev.prenticeapp.data.remote.response.deployed.FeedResponse
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForYouViewModel(private val userRepository: UserRepository) : ViewModel() {
    //    private val _reviewFeedResponse = MutableLiveData<List<ReviewFeedItems>>()
//    val reviewFeedDataResponse: LiveData<List<ReviewFeedItems>> = _reviewFeedResponse
    private val _reviewFeedResponse = MutableLiveData<FeedResponse>()
    val reviewFeedResponse: LiveData<FeedResponse> = _reviewFeedResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getFeedData()
    }

    fun getFeedData() {
        _isLoading.value = true
        try {
            val apiService = ApiConfig.getApiService()
            val client = apiService.getFeedData()
            client.enqueue(object : Callback<FeedResponse> {
                override fun onResponse(
                    call: Call<FeedResponse>,
                    response: Response<FeedResponse>
                ) {
                    _reviewFeedResponse.value = response.body()
                }

                override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                    Log.e(TAG, "Failed to fetch feed data!: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "Failed to call getFeedData API! ${e.message.toString()}")
        } finally {
            _isLoading.value = false
        }
    }

//    fun getFeedData() {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val apiService = ApiConfig.getApiService()
//                val response = withContext(Dispatchers.IO) {
//                    apiService.getReviewFeed().execute()
//                }
//                if (response.isSuccessful) {
//                    _reviewFeedResponse.value = response.body()?.data?.filterNotNull()
//                } else {
//                    Log.e(TAG, "Failed to fetch feed data ${response.errorBody()?.toString()}")
//                }
//            } catch (e: HttpException) {
//                Log.e(TAG, "Failed to fetch feed data ${e.message()}")
//
//            } finally {
//                _isLoading.value = false
//            }
//
//        }
//    }

    companion object {
        private val TAG = ForYouViewModel::class.java.simpleName
    }
}