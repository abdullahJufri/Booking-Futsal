package com.bangkit.booking_futsal.module.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.bangkit.booking_futsal.R
import com.bangkit.booking_futsal.module.auth.login.LoginFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager.commit {
            replace(R.id.fragment_container, LoginFragment(), LoginFragment::class.java.simpleName)
        }
    }
}