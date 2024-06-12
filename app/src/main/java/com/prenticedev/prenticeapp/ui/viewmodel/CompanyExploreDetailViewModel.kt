package com.prenticedev.prenticeapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prenticedev.prenticeapp.data.remote.response.CompanyResponseItem
import com.prenticedev.prenticeapp.data.repository.UserRepository

class CompanyExploreDetailViewModel(userRepository: UserRepository) : ViewModel() {
    private val _detailCompanyData = MutableLiveData<CompanyResponseItem>()
    val detailCompanyData: LiveData<CompanyResponseItem> = _detailCompanyData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailCompany(idCompany: String) {
        _isLoading.value = true
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