package com.bangkit.booking_futsal.module.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.databinding.ActivitySplashScreenBinding
import com.bangkit.booking_futsal.module.auth.AuthActivity
import com.bangkit.booking_futsal.module.main.MainActivity
import com.bangkit.booking_futsal.module.main.MainViewmodels
import com.bangkit.booking_futsal.utils.ViewModelFactory

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewmodels


    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        supportFragmentManager.commit {
//            replace(R.id.fragment_container, LoginFragment(), LoginFragment::class.java.simpleName)
//        }
        setupViewModel()


    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(SettingPreferences.getInstance(dataStore), this)
        )[MainViewmodels::class.java]

        mainViewModel.getUser().observe(this) {
            if (it.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
