package com.prenticedev.prenticeapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.prenticedev.prenticeapp.data.repository.UserRepository

class UserProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getToken(): LiveData<String> =
        userRepository.getToken()

    suspend fun deleteSession() =  userRepository.logout()

}