package com.bangkit.booking_futsal.utils

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

//    fun getUser(): Flow<Auth> {
//        return dataStore.data.map {
//            Auth(
//                it[NAME_KEY] ?: "",
//                it[EMAIL_KEY] ?: "",
//                it[PASSWORD_KEY] ?: "",
//                it[USERID_KEY] ?: "",
//                it[ROLES_KEY] ?: "",
//                it[STATE_KEY] ?: false
//            )
//        }
//    }
//
//    suspend fun saveUser(auth: Auth) {
//        dataStore.edit {
//            it[NAME_KEY] = auth.name
//            it[EMAIL_KEY] = auth.email
//            it[PASSWORD_KEY] = auth.password
//            it[USERID_KEY] = auth.userId
//            it[ROLES_KEY] = auth.roles
//            it[STATE_KEY] = auth.isLogin
//
//        }
//    }
//
//    suspend fun logout() {
//        dataStore.edit {
//            it[STATE_KEY] = false
//            it[NAME_KEY] = ""
//            it[EMAIL_KEY] = ""
//            it[USERID_KEY] = ""
//            it[ROLES_KEY] = ""
//            it[PASSWORD_KEY] = ""
//        }
//    }


    fun isFirstTime(): Flow<Boolean> = dataStore.data.map {
        it[STATE_KEY] ?: true
    }

    suspend fun setIsFirstTime(firstTime: Boolean) {
        dataStore.edit {
            it[STATE_KEY] = firstTime
        }
    }


    fun getUserID(): Flow<String> = dataStore.data.map {
        it[USERID_KEY] ?: DEFAULT_VALUE
    }

    suspend fun setUserID(id: String) {
        dataStore.edit {
            it[USERID_KEY] = id
            Log.e("SettingPreference", "user ID saved! saveUserToken: $id")
        }
    }

    fun getUserName(): Flow<String> = dataStore.data.map {
        it[NAME_KEY] ?: DEFAULT_VALUE
    }

    suspend fun setUserName(name: String) {
        dataStore.edit {
            it[NAME_KEY] = name
        }
    }

    fun getUserEmail(): Flow<String> = dataStore.data.map {
        it[EMAIL_KEY] ?: DEFAULT_VALUE
    }

    suspend fun setUserEmail(email: String) {
        dataStore.edit {
            it[EMAIL_KEY] = email
        }
    }

    fun getUserRoles(): Flow<String> = dataStore.data.map {
        it[ROLES_KEY] ?: DEFAULT_VALUE
    }

    suspend fun setUserRoles(email: String) {
        dataStore.edit {
            it[ROLES_KEY] = email
        }
    }

    suspend fun clearCache() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null
        const val DEFAULT_VALUE = "not_set_yet"
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val USERID_KEY = stringPreferencesKey("userId")
        private val ROLES_KEY = stringPreferencesKey("roles")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}