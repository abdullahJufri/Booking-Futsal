package com.bangkit.booking_futsal.data.remote.model

import com.google.gson.annotations.SerializedName

data class FustalsResponse(

	@field:SerializedName("data")
	val data: List<FutsalsItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class FutsalsItem(

	@field:SerializedName("jumlah_lapangan")
	val jumlahLapangan: String? = null,

	@field:SerializedName("jam_buka")
	val jamBuka: String? = null,

	@field:SerializedName("jam_tutup")
	val jamTutup: String? = null,

	@field:SerializedName("alamat_lapangan")
	val alamatLapangan: String? = null,

	@field:SerializedName("maps")
	val maps: String? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("id_pengelola")
	val idPengelola: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("harga_malam")
	val hargaMalam: String? = null,

	@field:SerializedName("harga_pagi")
	val hargaPagi: String? = null
)
