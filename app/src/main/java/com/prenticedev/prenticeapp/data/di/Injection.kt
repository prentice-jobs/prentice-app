package com.prenticedev.prenticeapp.data.di

import android.content.Context
import com.prenticedev.prenticeapp.data.local.pref.UserPreference
import com.prenticedev.prenticeapp.data.local.pref.dataStore
import com.prenticedev.prenticeapp.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(dataStore = context.dataStore)
        return UserRepository.getInstance(pref)
    }
}