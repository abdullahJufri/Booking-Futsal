package com.bangkit.booking_futsal.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Auth(
    val name: String,
    val email: String,
    val password: String,
    val id: String,
    val roles: String,
    val isLogin: Boolean
): Parcelable