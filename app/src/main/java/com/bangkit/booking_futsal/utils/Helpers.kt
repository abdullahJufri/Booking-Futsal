package com.bangkit.booking_futsal.utils

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast

interface LoginCallbackString {
    fun onResponse(success: String, message: String,)
}

fun showLoading(isLoading: Boolean, view: View) {
    if (isLoading) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}

fun showToast(context: Context, text: String) {
    Toast.makeText(
        context,
        text,
        Toast.LENGTH_SHORT
    ).show()
}

//private fun showAlertToast(param: Boolean, message: String) = if (param) {
//    showToast(this@LoginActivity, ", $message")
//    val intent = Intent(this@LoginActivity, MainActivity::class.java)
//    intent.flags =
//        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//    startActivity(intent)
//    finish()
//} else {
//    showToast(this@LoginActivity, message)
//    binding.progressBar.visibility = View.GONE
//}