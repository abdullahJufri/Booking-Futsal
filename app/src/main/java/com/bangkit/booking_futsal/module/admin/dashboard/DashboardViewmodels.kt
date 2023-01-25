package com.bangkit.booking_futsal.module.admin.dashboard

import android.util.Log
import androidx.lifecycle.*
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewmodels(private val pref: SettingPreferences) : ViewModel() {
    private val _itemFutsal = MutableLiveData<DashboardItem>()
    val itemFutsal: LiveData<DashboardItem> = _itemFutsal

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isHaveData = MutableLiveData<Boolean>()


    fun getFutsal(idPengelola: String) {
        _isLoading.value = true
        _isHaveData.value = true
        val client = ApiConfig
            .getApiService()
            .getAdminFutsal(idPengelola)

        client.enqueue(object : Callback<DashboardResponse> {
            override fun onResponse(
                call: Call<DashboardResponse>,
                response: Response<DashboardResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.data != null) {
                            _itemFutsal.value = response.body()?.data
                            _isHaveData.value =
                                responseBody.message == "history fetched successfully"
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure2: ${t.message}")
            }
        })
    }

    fun getUser(): LiveData<Auth> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(auth: Auth) {
        viewModelScope.launch {
            pref.saveUser(auth)
        }
    }
    fun logout() {
        _isLoading.value = true
        viewModelScope.launch {
            pref.logout()
            _isLoading.value = false
        }
    }


    companion object {
        private const val TAG = "dashboardViewmodel"
    }
}