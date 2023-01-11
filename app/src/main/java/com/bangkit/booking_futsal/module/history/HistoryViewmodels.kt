package com.bangkit.booking_futsal.module.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewmodels(private val pref: SettingPreferences) : ViewModel() {
    private val _itemHistory = MutableLiveData<List<HistoryItem>>()
    val itemHistory: LiveData<List<HistoryItem>> = _itemHistory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isHaveData = MutableLiveData<Boolean>()


    fun showListHistory(idUser: String) {
        _isLoading.value = true
        _isHaveData.value = true
        val client = ApiConfig
            .getApiService()
            .getUserHistory(idUser)

        client.enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.data != null) {
                            _itemHistory.value = response.body()?.data as List<HistoryItem>?
                            _isHaveData.value =
                                responseBody.message == "history fetched successfully"
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUser(): LiveData<Auth> {
        return pref.getUser().asLiveData()
    }

    companion object {
        private const val TAG = "historyViewmodel"
    }
}