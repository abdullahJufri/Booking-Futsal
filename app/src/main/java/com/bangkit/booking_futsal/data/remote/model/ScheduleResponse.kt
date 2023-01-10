package com.bangkit.booking_futsal.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleResponse(

	@field:SerializedName("data")
	val data: List<ScheduleItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) :Parcelable

@Parcelize
data class ScheduleItem(

	@field:SerializedName("jam")
	val jam: String? = null
) :Parcelable
