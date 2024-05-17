package com.prenticedev.prenticeapp.data.local.preferences

import android.content.Context

internal class UserPreferences(context: Context) {
    companion object {
        private const val PREF_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val AVATAR = "avatar"
    }

    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setUser(value: UserModel) {
        val editor = preferences.edit()
        editor.putString("name", value.name)
        editor.putString("email", value.email)
        editor.putString("avatar", value.avatar)
        editor.apply()
    }


    fun getUser(): UserModel {
        val model = UserModel()
        model.name = preferences.getString(NAME, "")
        model.email = preferences.getString(EMAIL, "")
        model.avatar = preferences.getString(AVATAR, "")
        return model
    }
}