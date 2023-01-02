package com.bangkit.booking_futsal.utils

import android.content.Context
import com.bangkit.booking_futsal.dataStore

object Injection {
    fun provideUserRepository(context: Context): Repository {
        val appExecutors = AppExecutors()
        val pref = SettingPreferences.getInstance(context.dataStore)
//        val apiService = ApiConfig.getApiService()
//        val storyDatabase = StoryDatabase.getDatabase(context)



        return Repository.getInstance(pref, appExecutors)
    }
}