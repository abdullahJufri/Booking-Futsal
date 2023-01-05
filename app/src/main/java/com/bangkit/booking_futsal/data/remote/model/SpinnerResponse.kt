package com.bangkit.booking_futsal.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class SpinnerResponse(


	@field:SerializedName("data")
	val data: List<DataSpinner?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DataSpinner(

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("value")
	val value: String? = null
) :Parcelable
