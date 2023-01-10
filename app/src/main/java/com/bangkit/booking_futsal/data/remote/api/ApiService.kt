package com.bangkit.booking_futsal.data.remote.api

import com.bangkit.booking_futsal.data.remote.model.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
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

    @GET("futsal")
    fun getUserFutsal(): Call<FustalsResponse>

    @FormUrlEncoded
    @POST("futsal/dd")
    fun getSpinner(
        @Field("id") id: String,
    ): Call<SpinnerResponse>

    @FormUrlEncoded
    @POST("schedule")
    fun getSchedule(
        @Field("id_futsal") idFutsal: String,
        @Field("id_lapangan") idLapangan: String,
        @Field("tanggal") tanggal: String,
    ): Call<ScheduleResponse>


}