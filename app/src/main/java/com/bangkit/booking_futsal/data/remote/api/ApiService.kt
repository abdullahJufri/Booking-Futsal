package com.bangkit.booking_futsal.data.remote.api

import com.bangkit.booking_futsal.data.remote.model.*
import retrofit2.Call
import retrofit2.http.*

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

    @FormUrlEncoded
    @POST("schedule/insert")
    fun insertSchedule(
        @Field("id_futsal") idFutsal: String,
        @Field("id_lapangan") idLapangan: String,
        @Field("tanggal") tanggal: String,
        @Field("jam") jam: String,
        @Field("id_user") idUser: String,
        @Field("harga") harga: String,
        @Field("order_id") orderID: String,
        @Field("status") status: String,
    ): Call<InsertResponse>

    @FormUrlEncoded
    @POST("history")
    fun getUserHistory(
        @Field("id_user") idUser: String,
    ): Call<HistoryResponse>

    @FormUrlEncoded
    @POST("history/update")
    fun updateHistory(
        @Field("order_id") orderId: String,
        @Field("status") status: String,
    ): Call<InsertResponse>

    //Midtrans

    @GET("{order_id}/status")
    @Headers("Authorization: Basic U0ItTWlkLXNlcnZlci1MTTB4MHpFRHhPc2xXdnZvMlg3dDlXTFo6")
    fun getMidtransStatus(
        @Path("order_id") orderID: String,
    ): Call<MidtransResponse>


    //Admin
    @FormUrlEncoded
    @POST("admin/check")
    fun getUserStatus(
        @Field("id_futsal") idFutsal: String,
        @Field("order_id") orderID: String,
    ): Call<CheckResponse>

    @FormUrlEncoded
    @POST("admin/futsal")
    fun getAdminFutsal(
        @Field("id_pengelola") idPengelola: String,
    ): Call<DashboardResponse>

    @FormUrlEncoded
    @POST("admin/schedule")
    fun getTransaksi(
        @Field("id_futsal") idFutsal: String,
        @Field("updated_at") updatedAt: String,
    ): Call<TransaksiResponse>

    @FormUrlEncoded
    @POST("admin/futsal/update")
    fun updateFutsal(
        @Field("id_pengelola") idPengelola: String,
        @Field("alamat_lapangan") alamatLapangan: String,
        @Field("jumlah_lapangan") jumlahLapangan: String,
        @Field("harga_pagi") jamBuka: String,
        @Field("harga_malam") jamTutup: String,
//        @Field("harga") harga: String,
    ): Call<InsertResponse>
}