package com.bangkit.booking_futsal.module.history.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.HistoryItem
import com.bangkit.booking_futsal.data.remote.model.InsertResponse
import com.bangkit.booking_futsal.data.remote.model.MidtransResponse
import com.bangkit.booking_futsal.utils.AuthCallbackString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryDetailViewmodels : ViewModel() {
    lateinit var hystoriesItem: HistoryItem

    fun setDetailHistory(history: HistoryItem): HistoryItem {
        hystoriesItem = history
        return hystoriesItem
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _itemMidtrans = MutableLiveData<MidtransResponse>()
    val itemMidtrans: LiveData<MidtransResponse> = _itemMidtrans
    private val _isHaveData = MutableLiveData<Boolean>()

    fun getMidtrans(order_id: String) {
        _isLoading.value = true
//        _isHaveData.value = true
        val client = ApiConfig
            .getMidtransService()
            .getMidtransStatus(order_id)

        client.enqueue(object : Callback<MidtransResponse> {
            override fun onResponse(
                call: Call<MidtransResponse>,
                response: Response<MidtransResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _itemMidtrans.value = responseBody
                        Log.e(TAG, "onResponse: ${response}")
                    } else{
                        Log.e(TAG, "onResponse: ${response}")
                    }

//                    _itemMidtrans.value = responseBody
//                    Log.e(TAG, "onResponse1: ${response}")

                    Log.e(TAG, "onResponse: ${responseBody}")
                } else {
                    Log.e(TAG, "onFailure: $response")
                }
            }

            override fun onFailure(call: Call<MidtransResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun update(
        orderId: String,
        status: String,
        callback: AuthCallbackString
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().updateHistory(orderId,status)
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
        private const val TAG = "DetailHistoryViewmodel"
    }
}