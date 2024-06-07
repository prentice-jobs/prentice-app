package com.prenticedev.prenticeapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prenticedev.prenticeapp.data.local.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _sessionStatus = MutableLiveData<Boolean>()
    val sessionStatus: LiveData<Boolean> = _sessionStatus

//    fun saveSession() = viewModelScope.launch { userRepository.setSession(UserModel(true)) }

    //    suspend fun checkSession() = userRepository.getSession()
     fun checkSession(): Flow<Boolean> =userRepository.getSession()
}