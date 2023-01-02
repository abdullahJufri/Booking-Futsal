package com.bangkit.booking_futsal.data.remote.api

import com.bangkit.booking_futsal.data.remote.model.UsersResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<UsersResponse>


    @FormUrlEncoded
    @POST("register")
    fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<UsersResponse>
}