package com.bangkit.booking_futsal.module.auth

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.UsersResponse
import com.bangkit.booking_futsal.utils.LoginCallbackString
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthViewmodels : ViewModel() {

    private var _user = MutableLiveData<UsersResponse>()
    val user: LiveData<UsersResponse> = _user

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String, callback: LoginCallbackString) {
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
                    Log.e("hore1", "onResponse: ${response.body()}")
                    callback.onResponse(responseBody.success.toString(), responseBody.message.toString())
                } else {
                    Log.e("hore2", "onFailure: ${response.message()}")
//                    val jsonObject =
//                        JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
//                    val success = jsonObject.getBoolean("success")
//                    val message = jsonObject.getString("message")

                    callback.onResponse(responseBody?.success.toString(),responseBody?.message.toString())

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


}