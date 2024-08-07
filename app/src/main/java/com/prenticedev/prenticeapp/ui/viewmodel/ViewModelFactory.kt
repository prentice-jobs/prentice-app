package com.prenticedev.prenticeapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prenticedev.prenticeapp.data.di.Injection
import com.prenticedev.prenticeapp.data.repository.UserRepository

class ViewModelFactory private constructor(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                SignInViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(ExploreViewModel::class.java) -> {
                ExploreViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(CompanyExploreDetailViewModel::class.java) -> {
                CompanyExploreDetailViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(ReviewViewModel::class.java) -> {
                ReviewViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(UserProfileViewModel::class.java) -> {
                UserProfileViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(ForYouViewModel::class.java) -> {
                ForYouViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(DetailReviewViewModel::class.java) ->{
                DetailReviewViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(SetUserPreferencesViewModel::class.java) ->{
                SetUserPreferencesViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(CompareFragmentViewModel::class.java)->{
                CompareFragmentViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(CompareRole2ViewModel::class.java)->{
                CompareRole2ViewModel(userRepository) as T
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