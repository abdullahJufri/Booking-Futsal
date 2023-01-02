package com.bangkit.booking_futsal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.bangkit.booking_futsal.databinding.ActivityMainBinding
import com.bangkit.booking_futsal.ui.auth.login.LoginFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.commit {
            replace(R.id.fragment_container, LoginFragment(), LoginFragment::class.java.simpleName)
        }
    }
}