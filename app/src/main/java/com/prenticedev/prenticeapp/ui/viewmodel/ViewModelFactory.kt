package com.prenticedev.prenticeapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prenticedev.prenticeapp.data.di.Injection
import com.prenticedev.prenticeapp.data.local.repository.UserRepository

class ViewModelFactory private constructor(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                SignInViewModel(userRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown Model Class: " + modelClass.name)
        }
    }

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            val userRepository = Injection.provideRepository(context)
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(userRepository)
            }.also { instance = it }
        }
    }
}