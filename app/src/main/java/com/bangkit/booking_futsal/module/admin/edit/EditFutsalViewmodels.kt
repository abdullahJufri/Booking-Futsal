package com.bangkit.booking_futsal.module.admin.edit

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.DashboardItem
import com.bangkit.booking_futsal.data.remote.model.InsertResponse
import com.bangkit.booking_futsal.utils.AuthCallbackString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditFutsalViewmodels : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    lateinit var futsalsItem: DashboardItem
    fun getDataIntent(futsal: DashboardItem): DashboardItem {
        futsalsItem = futsal
        return futsalsItem
    }

    fun update(
        idPengelola: String,
        alamatLapangan: String,
        jumlahLapangan: String,
        hargaPagi: String,
        hargaMalam: String,
        callback: AuthCallbackString
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().updateFutsal(idPengelola, alamatLapangan, jumlahLapangan, hargaPagi, hargaMalam)
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
}