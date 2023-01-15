package com.bangkit.booking_futsal.module.admin.transaksi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransaksiViewmodels : ViewModel() {
    private val _itemTransaksi = MutableLiveData<List<TransaksiItem>>()
    val itemTransaksi: LiveData<List<TransaksiItem>> = _itemTransaksi

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isHaveData = MutableLiveData<Boolean>()

    lateinit var futsalsItem: DashboardItem
    fun getDataIntent(futsal: DashboardItem): DashboardItem {
        futsalsItem = futsal
        return futsalsItem
    }

    fun showListTransaksi(idUser: String, updatedAt: String) {
        _isLoading.value = true
        _isHaveData.value = true
        val client = ApiConfig
            .getApiService()
            .getTransaksi(idUser, updatedAt)

        client.enqueue(object : Callback<TransaksiResponse> {
            override fun onResponse(
                call: Call<TransaksiResponse>,
                response: Response<TransaksiResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.data != null) {
                            _itemTransaksi.value = response.body()?.data as List<TransaksiItem>?
                            _isHaveData.value =
                                responseBody.message == "history fetched successfully"
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TransaksiResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "transaksiViewmodel"
    }

}