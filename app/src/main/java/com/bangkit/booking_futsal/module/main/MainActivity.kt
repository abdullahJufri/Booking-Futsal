package com.bangkit.booking_futsal.module.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.booking_futsal.R
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.databinding.ActivityMainBinding
import com.bangkit.booking_futsal.module.auth.login.LoginFragment
import com.bangkit.booking_futsal.module.splashscreen.dataStore
import com.bangkit.booking_futsal.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewmodels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navItems
        supportActionBar?.hide()


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
//        supportFragmentManager.commit {
//            replace(R.id.fragment_container, LoginFragment(), LoginFragment::class.java.simpleName)
//        }
    }


}