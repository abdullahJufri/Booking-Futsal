package com.bangkit.booking_futsal.utils

import android.content.Context
import android.content.SharedPreferences
import com.bangkit.booking_futsal.data.remote.model.Auth
import com.bangkit.booking_futsal.module.auth.login.LoginFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences(context: Context) {
    val preference: SharedPreferences = context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)


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

    fun setUserLogin( id: Int, email: String, roles: String ) {
        preference.edit()
            .putString(EMAIL, email)
            .putInt(ID, id)
            .putString(Roles, roles)
            .apply()
    }


    fun deleteUserLogin() {
        preference.edit()
            .putString(EMAIL, "")
            .putInt(ID, 0)
            .putString(Roles, "")
            .putString(PATH, "")
            .apply()
    }

    companion object {
        const val LOGIN_SESSION = "login_session"
        const val EMAIL = "email"
        const val ID = "id"
        const val Roles = "roles"
        const val PATH = "path"
    }
}