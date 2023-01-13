package com.bangkit.booking_futsal.module.admin.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.data.remote.model.Auth
import com.bangkit.booking_futsal.databinding.ActivityDashboardBinding
import com.bangkit.booking_futsal.module.admin.check.CheckActivity
import com.bangkit.booking_futsal.module.splashscreen.dataStore
import com.bangkit.booking_futsal.utils.ViewModelFactory
import com.bangkit.booking_futsal.utils.showLoading

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var viewmodels: DashboardViewmodels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewmodel()


        binding.btnCekBooking.setOnClickListener {
            intent = android.content.Intent(this, CheckActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Cek Booking", Toast.LENGTH_SHORT).show()
        }
    }

    fun setupViewmodel() {
        viewmodels = ViewModelProvider(
            this,
            ViewModelFactory(SettingPreferences.getInstance(dataStore), this)
        )[DashboardViewmodels::class.java]
        viewmodels.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }
        viewmodels.getUser().observe(this) {
            viewmodels.getFutsal(it.id)
            val name = it.name
            val email = it.email
            val id = it.id
            val roles = it.roles
            viewmodels.itemFutsal.observe(this) {
                val model = Auth(
                    name,
                    email,
                    id,
                    roles,
                    true,
                    it.id,
                )
                viewmodels.saveUser(model)
                Log.e("Futsal", it.toString())
            }
        }
    }
}