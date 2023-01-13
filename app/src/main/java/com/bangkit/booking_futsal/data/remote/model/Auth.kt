package com.bangkit.booking_futsal.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Auth(
    val name: String,
    val email: String,
    val id: String,
    val roles: String,
    val isLogin: Boolean,
    val futsal_id: String? = null
): Parcelable