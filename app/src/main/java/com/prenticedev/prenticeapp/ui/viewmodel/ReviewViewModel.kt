package com.prenticedev.prenticeapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prenticedev.prenticeapp.data.remote.response.CompanyResponseItem
import com.prenticedev.prenticeapp.data.repository.UserRepository

class ReviewViewModel(userRepository: UserRepository) : ViewModel() {
    private val _companyData = MutableLiveData<CompanyResponseItem>()
    val companyData: LiveData<CompanyResponseItem> = _companyData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


}