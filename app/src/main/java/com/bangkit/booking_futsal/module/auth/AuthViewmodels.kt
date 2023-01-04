package com.bangkit.booking_futsal.module.auth

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.Auth
import com.bangkit.booking_futsal.data.remote.model.UsersResponse
import com.bangkit.booking_futsal.utils.AuthCallbackString
import com.bangkit.booking_futsal.data.local.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthViewmodels(private val pref: SettingPreferences) : ViewModel() {

    private var _user = MutableLiveData<UsersResponse>()
    val user: LiveData<UsersResponse> = _user

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String, callback: AuthCallbackString) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().userLogin(email, password)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(
                call: Call<UsersResponse>,
                response: Response<UsersResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (responseBody?.data != null) {
                    _user.value = response.body()
                    val model = Auth(
                        responseBody.data.name.toString(),
                        email,
                        responseBody.data.id.toString(),
                        responseBody.data.roles.toString(),
                        true
                    )
                    saveUser(model)
                    Log.e("hore1", "onResponse: ${response.body()}")
                    callback.onResponse(
                        responseBody.success.toString(),
                        responseBody.message.toString()
                    )
                } else {
                    Log.e("hore2", "onFailure: ${response.message()}")

                    callback.onResponse(
                        responseBody?.success.toString(),
                        responseBody?.message.toString()
                    )

                    Log.e("hore3", "onFailure: ${responseBody?.message}")
                    Log.e("hore3", "onFailure: $responseBody")
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun saveUser(auth: Auth) {
        viewModelScope.launch {
            pref.saveUser(auth)
        }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        callback: AuthCallbackString
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().userRegister(name, email, password)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(
                call: Call<UsersResponse>,
                response: Response<UsersResponse>,
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

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


}