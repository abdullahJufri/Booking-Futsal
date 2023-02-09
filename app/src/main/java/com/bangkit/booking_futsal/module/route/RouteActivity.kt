package com.bangkit.booking_futsal.module.route

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.booking_futsal.R
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.module.admin.dashboard.DashboardActivity
import com.bangkit.booking_futsal.module.auth.AuthActivity
import com.bangkit.booking_futsal.module.main.MainActivity
import com.bangkit.booking_futsal.module.main.MainViewmodels
import com.bangkit.booking_futsal.module.splashscreen.dataStore
import com.bangkit.booking_futsal.utils.ViewModelFactory

class RouteActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewmodels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        setupViewModel()
    }

    private fun setupViewModel() {

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(SettingPreferences.getInstance(dataStore), this)
        )[MainViewmodels::class.java]
        mainViewModel.getUser().observe(this) {
            val roles = it.roles
            when (roles) {
                "1" -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                "0" -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
//            if (roles == "1") {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//                onBackPressed()
//
//
//            } else {
//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
//                onBackPressed()
//            }

        }


    }
}