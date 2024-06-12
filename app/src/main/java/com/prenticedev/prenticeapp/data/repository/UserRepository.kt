package com.prenticedev.prenticeapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.prenticedev.prenticeapp.data.local.pref.UserModel
import com.prenticedev.prenticeapp.data.local.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference
) {

    suspend fun saveSession(user: UserModel) = userPreference.saveSession(user)
    fun getSession(): Flow<UserModel> = userPreference.getSession()
    fun getToken(): LiveData<String> = userPreference.getToken().asLiveData()
    fun getName(): LiveData<String> = userPreference.getName().asLiveData()
    suspend fun logout() = userPreference.logOut()

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(
                userPreference
            )
        }.also { instance = it }
    }
}