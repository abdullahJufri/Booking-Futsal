package com.bangkit.booking_futsal.data.remote.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(

	@field:SerializedName("data")
	val data: User? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class User(

//	@field:SerializedName("password")
//	val password: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("roles")
	val roles: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: Any? = null
)
