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

class ExploreViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _companyData = MutableLiveData<List<CompanyResponseItem>?>()
    val companyData: LiveData<List<CompanyResponseItem>?> get() = _companyData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getCompanies()
    }

    fun searchCompanyByName(companyName: String? = null) {
        if (companyName.isNullOrEmpty()) {
            getCompanies()
        } else {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val response = withContext(Dispatchers.IO) {
                        apiService.searchCompany(companyName).execute()
                    }
                    if (response.isSuccessful) {
                        _companyData.postValue(response.body())
                    } else {
                        Log.e(TAG, "Failed to search company: ${response.errorBody()}")
                    }

                } catch (e: HttpException) {
                    Log.e(TAG, "Failed to make API request ${e.message()}")
                } finally {
                    _isLoading.postValue(false)
                }
            }
        }
    }

     fun getCompanies() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val response = withContext(Dispatchers.IO) {
                    apiService.getCompanies().execute()
                }
                if (response.isSuccessful) {
                    _companyData.postValue(response.body())
                } else {
                    Log.e(
                        TAG,
                        "Failed to retrieve list of company data ${response.errorBody()}"
                    )
                }
            } catch (e: HttpException) {
                Log.e(TAG, "getCompany() method error ${e.message()}")
            } finally {
                _isLoading.postValue(false)
            }
        }

    }

    companion object {
        private val TAG = ExploreViewModel::class.java.simpleName
    }
}

