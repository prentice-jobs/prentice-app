package com.prenticedev.prenticeapp.data.local.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun setSignInStatus(userModel: UserModel) {
        dataStore.edit { pref ->
            pref[IS_LOGIN_KEY] = userModel.isLogin
        }
    }

    fun getSignInStatus(): Flow<Boolean> {
        return dataStore.data.map { pref ->
            pref[IS_LOGIN_KEY] ?: false
        }
    }

    companion object {
        private var INSTANCE: UserPreference? = null
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}