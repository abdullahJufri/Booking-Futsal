package com.bangkit.booking_futsal.module.admin.check

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.*
import com.bangkit.booking_futsal.utils.AuthCallbackString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckViewmodels: ViewModel() {
    private val _itemCheck = MutableLiveData<CheckItem>()
    val itemCheck: LiveData<CheckItem> = _itemCheck

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isHaveData = MutableLiveData<Boolean>()


    fun checkOrder(orderID: String,idFutsal: String,callback: AuthCallbackString) {
        _isLoading.value = true
        _isHaveData.value = true
        val client = ApiConfig
            .getApiService()
            .getUserStatus(orderID,idFutsal)

        client.enqueue(object : Callback<CheckResponse> {
            override fun onResponse(
                call: Call<CheckResponse>,
                response: Response<CheckResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.data != null) {
                            callback.onResponse(
                                responseBody.success.toString(),
                                responseBody.message.toString()
                            )
                            _itemCheck.value = response.body()?.data
                            _isHaveData.value =
                                responseBody.message == "history fetched successfully"
                        } else{
                            callback.onResponse(
                                responseBody.success.toString(),
                                responseBody.message.toString()
                            )
                            _isHaveData.value =
                                responseBody.message == "Data Tidak Ditemukan"
                            Log.e(TAG, "onFailure: ${responseBody.message}")

                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }



    companion object {
        private const val TAG = "checkViewmodel"
    }
}