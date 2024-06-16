package com.prenticedev.prenticeapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prenticedev.prenticeapp.data.remote.response.DetailReviewResponse
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DetailReviewViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _detailReviewResponse = MutableLiveData<DetailReviewResponse>()
    val detailReviewResponse: LiveData<DetailReviewResponse> = _detailReviewResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailReview(idReview: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiService = ApiConfig.getApiService()
                val response = withContext(Dispatchers.IO) {
                    apiService.getReviewDetail(idReview).execute()
                }
                if (response.isSuccessful) {
                    _detailReviewResponse.value = response.body()
                } else {
                    Log.e(TAG, "Failed to retrieve detail review info!: ${response.errorBody()}")
                }
            } catch (e: HttpException) {
                Log.e(TAG, "Failed to connect getDetailReviewApi(): ${e.message()}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    companion object {
        private val TAG = DetailReviewViewModel::class.java.simpleName
    }
}