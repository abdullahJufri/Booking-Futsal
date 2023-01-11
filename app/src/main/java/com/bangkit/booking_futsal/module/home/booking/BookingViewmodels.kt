package com.bangkit.booking_futsal.module.home.booking

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.*
import com.bangkit.booking_futsal.utils.AuthCallbackString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingViewmodels : ViewModel() {
    private val _itemLapangan = MutableLiveData<List<SpinnerItems>>()
    val itemLapangan: LiveData<List<SpinnerItems>> = _itemLapangan

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isHaveData = MutableLiveData<Boolean>()
    lateinit var futsalsItem: FutsalsItem

    private val _itemJam = MutableLiveData<List<ScheduleItem>>()
    val itemJam: LiveData<List<ScheduleItem>> = _itemJam


    fun setDetailStory(futsal: FutsalsItem): FutsalsItem {
        futsalsItem = futsal
        return futsalsItem
    }

    fun showListLapangan(id: String) {
        _isLoading.value = true
        _isHaveData.value = true
        val client = ApiConfig
            .getApiService()
            .getSpinner(id)

        client.enqueue(object : Callback<SpinnerResponse> {
            override fun onResponse(
                call: Call<SpinnerResponse>,
                response: Response<SpinnerResponse>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    if (responseBody.data != null) {

                        _itemLapangan.value = response.body()!!.data as List<SpinnerItems>?
//                        _isHaveData.value =
//                            responseBody.message == "futsal fetched successfully"
                        Log.e(TAG, "berhasil: ${responseBody}")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SpinnerResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun showJam(idfutsal: String, idlapangan: String, tanggal: String) {
        _isLoading.value = true
        _isHaveData.value = true
        val client = ApiConfig
            .getApiService()
            .getSchedule(idfutsal, idlapangan, tanggal)

        client.enqueue(object : Callback<ScheduleResponse> {
            override fun onResponse(
                call: Call<ScheduleResponse>,
                response: Response<ScheduleResponse>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    if (responseBody.data != null) {

                        _itemJam.value = response.body()!!.data as List<ScheduleItem>?
//                        _isHaveData.value =
//                            responseBody.message == "futsal fetched successfully"
                        Log.e(TAG, "berhasil: ${responseBody}")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ScheduleResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun insert(
        idfutsal: String,
        idlapangan: String,
        tanggal: String,
        jam: String,
        idUser: String,
        harga: String,
        orderId: String,
        status: String,
        callback: AuthCallbackString
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().inserSchedule(idfutsal, idlapangan, tanggal, jam, idUser, harga, orderId,status)
        client.enqueue(object : Callback<InsertResponse> {
            override fun onResponse(
                call: Call<InsertResponse>,
                response: Response<InsertResponse>,
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (responseBody != null) {
                    callback.onResponse(
                        responseBody.success.toString(),
                        responseBody.message.toString()
                    )
                    Log.e(ContentValues.TAG, "onResponse: ${response.body()}")
                } else {
                    callback.onResponse(
                        responseBody?.success.toString(),
                        responseBody?.message.toString()
                    )
                    Log.e("regis", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<InsertResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


    companion object {
        private const val TAG = "bookingViewmodel"
    }

}