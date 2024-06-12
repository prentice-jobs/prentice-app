package com.prenticedev.prenticeapp.data.local.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) = dataStore.edit { pref ->
        pref[NAME_KEY] = user.username
        pref[TOKEN_KEY] = user.token
        pref[IS_LOGIN_KEY] = true
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { pref ->
            pref[TOKEN_KEY] ?: ""
        }
    }

    fun getName(): Flow<String> {
        return dataStore.data.map { pref ->
            pref[NAME_KEY] ?: ""
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { pref ->
            UserModel(
                pref[NAME_KEY] ?: "",
                pref[TOKEN_KEY] ?: "",
                pref[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logOut() {
        dataStore.edit { pref ->
            pref.clear()
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
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