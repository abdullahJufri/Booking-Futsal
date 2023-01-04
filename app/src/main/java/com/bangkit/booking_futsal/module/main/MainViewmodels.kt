package com.bangkit.booking_futsal.module.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.booking_futsal.data.remote.model.Auth
import com.bangkit.booking_futsal.data.local.SettingPreferences
import kotlinx.coroutines.launch

class MainViewmodels (private val pref: SettingPreferences) : ViewModel()  {

    fun getUser(): LiveData<Auth> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

}