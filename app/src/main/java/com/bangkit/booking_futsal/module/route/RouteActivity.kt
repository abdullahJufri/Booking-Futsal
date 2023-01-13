package com.bangkit.booking_futsal.module.route

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.bangkit.booking_futsal.R
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.module.auth.login.LoginFragment
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
            if (roles == "1") {
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, LoginFragment(), LoginFragment::class.java.simpleName)
                }

            } else{
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, LoginFragment(), LoginFragment::class.java.simpleName)
                }
            }

        }


    }
}