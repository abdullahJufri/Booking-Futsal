package com.bangkit.booking_futsal.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.booking_futsal.ui.auth.login.LoginViewmodels
import com.bangkit.booking_futsal.ui.auth.register.RegisterViewmodels

class ViewModelFactory private constructor(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewmodels::class.java) -> LoginViewmodels(repository) as T
            modelClass.isAssignableFrom(RegisterViewmodels::class.java) -> RegisterViewmodels(repository) as T
//            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> AddStoryViewModel(
//                repository
//            ) as T
//            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> SettingsViewModel(
//                repository
//            ) as T
//            modelClass.isAssignableFrom(MapsViewModel::class.java) -> MapsViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideUserRepository(context)
                )
            }.also { instance = it }
    }
}