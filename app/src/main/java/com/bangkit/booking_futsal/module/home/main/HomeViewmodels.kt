package com.bangkit.booking_futsal.module.home.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.FustalsResponse
import com.bangkit.booking_futsal.data.remote.model.FutsalsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewmodels : ViewModel() {
    private val _itemFutsal = MutableLiveData<List<FutsalsItem>>()
    val itemFutsal: LiveData<List<FutsalsItem>> = _itemFutsal

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isHaveData = MutableLiveData<Boolean>()


    fun showListFutsal() {
        _isLoading.value = true
        _isHaveData.value = true
        val client = ApiConfig
            .getApiService()
            .getUserFutsal()

        client.enqueue(object : Callback<FustalsResponse> {
            override fun onResponse(
                call: Call<FustalsResponse>,
                response: Response<FustalsResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.data != null) {
                            _itemFutsal.value = response.body()?.data as List<FutsalsItem>?
                            _isHaveData.value =
                                responseBody.message == "futsal fetched successfully"
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FustalsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    companion object {
        private const val TAG = "homeViewmodel"
    }
}