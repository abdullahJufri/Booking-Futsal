package com.bangkit.booking_futsal.module.home.booking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.FutsalsItem
import com.bangkit.booking_futsal.data.remote.model.SpinnerItems
import com.bangkit.booking_futsal.data.remote.model.SpinnerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingViewmodels : ViewModel() {
    private val _itemFutsal = MutableLiveData<List<SpinnerItems>>()
    val itemFutsal: LiveData<List<SpinnerItems>> = _itemFutsal

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isHaveData = MutableLiveData<Boolean>()
    lateinit var futsalsItem: FutsalsItem

    fun setDetailStory(futsal: FutsalsItem): FutsalsItem {
        futsalsItem = futsal
        return futsalsItem
    }

    fun showListFutsal(id: String) {
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

                        _itemFutsal.value = response.body()!!.data as List<SpinnerItems>?
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


    companion object {
        private const val TAG = "bookingViewmodel"
    }

}