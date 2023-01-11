package com.bangkit.booking_futsal.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InsertResponse(
	val success: Boolean? = null,
	val message: String? = null
):Parcelable

