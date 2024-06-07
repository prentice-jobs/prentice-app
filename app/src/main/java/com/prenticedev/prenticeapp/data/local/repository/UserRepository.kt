package com.prenticedev.prenticeapp.data.local.repository

import com.prenticedev.prenticeapp.data.local.pref.UserModel
import com.prenticedev.prenticeapp.data.local.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference
) {
    suspend fun setSession(isLogin: UserModel) = userPreference.setSignInStatus(isLogin)
    fun getSession(): Flow<Boolean> = userPreference.getSignInStatus()

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