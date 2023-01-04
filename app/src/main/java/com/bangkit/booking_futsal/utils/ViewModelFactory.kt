package com.bangkit.booking_futsal.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.module.auth.AuthViewmodels
import com.bangkit.booking_futsal.module.main.MainViewmodels

class ViewModelFactory(private val pref: SettingPreferences, private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewmodels::class.java) -> {
                MainViewmodels(pref) as T
            }
            modelClass.isAssignableFrom(AuthViewmodels::class.java) -> {
                AuthViewmodels(pref) as T
            }

//            modelClass.isAssignableFrom(MainViewmodels::class.java) -> {
//                MainViewmodels(pref, Injection.provideRepository(context)) as T
//            }
//            modelClass.isAssignableFrom(MapsViewmodels::class.java) -> {
//                MapsViewmodels(pref, Injection.provideRepository(context)) as T
//            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}