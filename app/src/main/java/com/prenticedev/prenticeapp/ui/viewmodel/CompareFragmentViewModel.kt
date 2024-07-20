package com.prenticedev.prenticeapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prenticedev.prenticeapp.data.remote.response.deployed.CompanyDeployedResponse
import com.prenticedev.prenticeapp.data.remote.response.deployed.RolesResponse
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompareFragmentViewModel(userRepository: UserRepository) : ViewModel() {
    private val _roleList = MutableLiveData<RolesResponse>()
    val roleList: LiveData<RolesResponse> = _roleList

    private val _companyList = MutableLiveData<CompanyDeployedResponse?>()
    val companyList: LiveData<CompanyDeployedResponse?> = _companyList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        setRoleValue()
        setCompanyValue()
    }

    private fun setCompanyValue() {
        _isLoading.value = true
        try {
            val apiService = ApiConfig.getApiService()
            val response = apiService.getCompanies()

            response.enqueue(object : Callback<CompanyDeployedResponse> {
                override fun onResponse(
                    call: Call<CompanyDeployedResponse>,
                    response: Response<CompanyDeployedResponse>
                ) {
                    _companyList.value = response.body()
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<CompanyDeployedResponse>, t: Throwable) {
                    _companyList.value = null
                    _isLoading.value = false
                    Log.e(TAG, "Failed to fetch company datas: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "Failed to call getCompany API: ${e.message.toString()}")
        }
    }

    private fun setRoleValue() {
        _isLoading.value = true
        try {
            val apiService = ApiConfig.getApiService()
            val response = apiService.getRoles()
            response.enqueue(object : Callback<RolesResponse> {
                override fun onResponse(
                    call: Call<RolesResponse>,
                    response: Response<RolesResponse>
                ) {
                    _roleList.value = response.body()
                }

                override fun onFailure(call: Call<RolesResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "setRoleValue ERROR!: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }


    companion object {
        private const val TAG = "CompareFragmentViewModel"
    }
}