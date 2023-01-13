package com.bangkit.booking_futsal.module.admin.check

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.CheckItem
import com.bangkit.booking_futsal.data.remote.model.CheckResponse
import com.bangkit.booking_futsal.data.remote.model.HistoryItem
import com.bangkit.booking_futsal.data.remote.model.HistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckViewmodels: ViewModel() {
    private val _itemCheck = MutableLiveData<CheckItem>()
    val itemCheck: LiveData<CheckItem> = _itemCheck

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isHaveData = MutableLiveData<Boolean>()


    fun checkOrder(orderID: String) {
        _isLoading.value = true
        _isHaveData.value = true
        val client = ApiConfig
            .getApiService()
            .getUserStatus(orderID)

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
                            _itemCheck.value = response.body()?.data
                            _isHaveData.value =
                                responseBody.message == "history fetched successfully"
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