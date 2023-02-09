package com.bangkit.booking_futsal.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bangkit.booking_futsal.data.remote.model.Auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val key = stringPreferencesKey("user_token")

    fun getUser(): Flow<Auth> {
        return dataStore.data.map {
            Auth(
                it[NAME_KEY] ?: "",
                it[EMAIL_KEY] ?: "",
                it[ID_KEY] ?: "",
                it[ROLES_KEY] ?: "",
                it[STATE_KEY] ?: false,
                it[FUTSAL_ID_KEY] ?: ""
            )
        }
    }

    suspend fun saveUser(auth: Auth) {
        dataStore.edit {
            it[NAME_KEY] = auth.name
            it[EMAIL_KEY] = auth.email
            it[ID_KEY] = auth.id
            it[ROLES_KEY] = auth.roles
            it[STATE_KEY] = auth.isLogin
            it[FUTSAL_ID_KEY] = auth.futsal_id ?: ""

        }
    }
    suspend fun logout() {
        dataStore.edit {
            it[STATE_KEY] = false
            it[NAME_KEY] = ""
            it[EMAIL_KEY] = ""
            it[ID_KEY] = ""
            it[ROLES_KEY] = ""
            it[FUTSAL_ID_KEY] = ""
        }
    }

//    fun getUser(): Flow<Auth> {
//        return preference.data.map {
//            Authentication(
//                it[NAME_KEY] ?: "",
//                it[EMAIL_KEY] ?: "",
//                it[PASSWORD_KEY] ?: "",
//                it[USERID_KEY] ?: "",
//                it[TOKEN_KEY] ?: "",
//                it[STATE_KEY] ?: false
//            )
//        }
//    }

//    fun getUserLogin( id: Int, email: String, roles: String ) {
//        preference.edit()
//            .putString(EMAIL, email)
//            .putInt(ID, id)
//            .putString(Roles, roles)
//            .apply()
//    }
//


//    fun setUserLogin( id: Int, email: String, roles: String ) {
//        preference.edit()
//            .putString(EMAIL, email)
//            .putInt(ID, id)
//            .putString(Roles, roles)
//            .apply()
//    }
//
//
//    fun deleteUserLogin() {
//        preference.edit()
//            .putString(EMAIL, "")
//            .putInt(ID, 0)
//            .putString(Roles, "")
//            .putString(PATH, "")
//            .apply()
//    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val ID_KEY = stringPreferencesKey("id")
        private val ROLES_KEY = stringPreferencesKey("roles")
        private val STATE_KEY = booleanPreferencesKey("state")
        private val FUTSAL_ID_KEY = stringPreferencesKey("futsal_id")

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}