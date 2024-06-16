package com.prenticedev.prenticeapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prenticedev.prenticeapp.data.remote.response.CompanyResponseItem
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class CompanyExploreDetailViewModel(userRepository: UserRepository) : ViewModel() {
    private val _detailCompanyData = MutableLiveData<CompanyResponseItem>()
    val detailCompanyData: LiveData<CompanyResponseItem> = _detailCompanyData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailCompany(idCompany: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val response = withContext(Dispatchers.IO) {
                    apiService.getDetailCompany(idCompany).execute()
                }
                if (response.isSuccessful) {
                    _detailCompanyData.value = response.body()
                } else {
                    Log.e(TAG, "Failed to fetch detail user data! ${response.errorBody()}")
                }
            } catch (e: HttpException) {
                Log.e(TAG, "Error when making getDetailCompany request! ${e.message()}")
            } finally {
                _isLoading.value = false
            }
        }
//        val client = ApiConfig.getApiServices().getDetailCompany(idCompany)
//        client.enqueue(object : Callback<CompanyResponseItem> {
//            override fun onResponse(
//                call: Call<CompanyResponseItem>,
//                response: Response<CompanyResponseItem>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    _detailCompanyData.value = response.body()
//                }
//            }
//
//            override fun onFailure(call: Call<CompanyResponseItem>, t: Throwable) {
//                Log.e(TAG, t.message.toString())
//            }
//        })
    }

    companion object {
        private val TAG = CompanyExploreDetailViewModel::class.java.simpleName
    }
}