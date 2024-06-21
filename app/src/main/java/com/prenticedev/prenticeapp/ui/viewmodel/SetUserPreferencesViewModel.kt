package com.prenticedev.prenticeapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prenticedev.prenticeapp.data.remote.response.deployed.IndustriesResponse
import com.prenticedev.prenticeapp.data.remote.response.deployed.LocationsResponse
import com.prenticedev.prenticeapp.data.remote.response.deployed.RolesResponse
import com.prenticedev.prenticeapp.data.remote.response.deployed.SetPreferenceRequest
import com.prenticedev.prenticeapp.data.remote.response.deployed.SetPreferenceResponse
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetUserPreferencesViewModel(userRepository: UserRepository) : ViewModel() {
    private val _industriesResponse = MutableLiveData<IndustriesResponse>()
    val industriesResponse: LiveData<IndustriesResponse> = _industriesResponse

    private val _rolesResponse = MutableLiveData<RolesResponse>()
    val rolesResponse: LiveData<RolesResponse> = _rolesResponse

    private val _locationsResponse = MutableLiveData<LocationsResponse>()
    val locationsResponse: LiveData<LocationsResponse> = _locationsResponse

    private val _savePreferenceResponse = MutableLiveData<SetPreferenceResponse?>()
    val savePreferenceResponse: LiveData<SetPreferenceResponse?> = _savePreferenceResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            getRolesValue()
            getIndustriesValue()
            getLocationsResponse()
        }
    }


    fun submitPreference(role: String, industry: String, location: String) {
        _isLoading.value = true
        try {
            val apiService = ApiConfig.getApiService()
            val client =
                apiService.saveUserPreference(SetPreferenceRequest(role, industry, location))
            client.enqueue(object : Callback<SetPreferenceResponse> {
                override fun onResponse(
                    call: Call<SetPreferenceResponse>,
                    response: Response<SetPreferenceResponse>
                ) {
                    _savePreferenceResponse.value = response.body()
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<SetPreferenceResponse>, t: Throwable) {
                    _savePreferenceResponse.value = null
                    _isLoading.value = false
                    Log.e(TAG, "Failed to submit the preference data! ${t.message.toString()}")
                }
            })

        } catch (e: Exception) {
            Log.e(TAG, "Failed to call saveUserPreference API! ${e.message.toString()}")
        }
    }


    private fun getRolesValue() {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        val client = apiService.getRoles()
        client.enqueue(object : Callback<RolesResponse> {
            override fun onResponse(call: Call<RolesResponse>, response: Response<RolesResponse>) {
                _isLoading.value = false
                _rolesResponse.value = response.body()
            }

            override fun onFailure(call: Call<RolesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failed to catch role api: ${t.message}")
            }
        })
    }

    private fun getIndustriesValue() {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        val client = apiService.getIndustries()
        client.enqueue(object : Callback<IndustriesResponse> {
            override fun onResponse(
                call: Call<IndustriesResponse>,
                response: Response<IndustriesResponse>
            ) {
                _isLoading.value = false
                _industriesResponse.value = response.body()
            }

            override fun onFailure(call: Call<IndustriesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failed to catch Industries api: ${t.message}")
            }
        })
    }

    private fun getLocationsResponse() {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        val client = apiService.getLocations()

        client.enqueue(object : Callback<LocationsResponse> {
            override fun onResponse(
                call: Call<LocationsResponse>,
                response: Response<LocationsResponse>
            ) {
                _isLoading.value = false
                _locationsResponse.value = response.body()
            }

            override fun onFailure(call: Call<LocationsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failed to make API call to Location API ${t.message.toString()}")
            }
        })
    }

    companion object {
        private val TAG = SetUserPreferencesViewModel::class.java.simpleName
    }
}