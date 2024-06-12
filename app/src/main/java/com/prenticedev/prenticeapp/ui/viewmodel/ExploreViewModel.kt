package com.prenticedev.prenticeapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prenticedev.prenticeapp.data.remote.response.CompanyResponseItem
import com.prenticedev.prenticeapp.data.repository.UserRepository

class ExploreViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _companyData = MutableLiveData<List<CompanyResponseItem>?>()
    val companyData: LiveData<List<CompanyResponseItem>?> get() = _companyData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getCompanies()
    }

    fun updateCompanyData(newData: List<CompanyResponseItem>?) {
        _companyData.value = newData ?: emptyList()
    }

    fun getCompanies() {
        _isLoading.value = true
//        val client = ApiConfig.getApiServices().getCompanies()
//        client.enqueue(object : Callback<List<CompanyResponseItem>?> {
//            override fun onResponse(
//                call: Call<List<CompanyResponseItem>?>,
//                response: Response<List<CompanyResponseItem>?>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) updateCompanyData(response.body()) else updateCompanyData(
//                    emptyList()
//                )
//            }
//
//            override fun onFailure(call: Call<List<CompanyResponseItem>?>, t: Throwable) {
//                _isLoading.value = false
//                updateCompanyData(emptyList())
//                Log.e(TAG, t.message.toString())
//            }
//        })

    }

    companion object {
        private val TAG = ExploreViewModel::class.java.simpleName
    }
}

