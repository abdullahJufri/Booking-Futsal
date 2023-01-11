package com.bangkit.booking_futsal.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class HistoryResponse(

    @field:SerializedName("data")
    val data: List<HistoryItem?>? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable

@Parcelize
data class HistoryItem(

    @field:SerializedName("id_futsal")
    val idFutsal: String? = null,

    @field:SerializedName("harga")
    val harga: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("jam")
    val jam: String? = null,

    @field:SerializedName("id_lapangan")
    val idLapangan: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("id_user")
    val idUser: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("order_id")
    val orderId: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("nama_lapangan")
    val nama_lapangan: String? = null

) : Parcelable
