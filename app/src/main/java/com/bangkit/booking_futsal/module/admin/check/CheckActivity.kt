package com.bangkit.booking_futsal.module.admin.check

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.databinding.ActivityCheckBinding
import com.bangkit.booking_futsal.module.main.MainViewmodels
import com.bangkit.booking_futsal.module.splashscreen.dataStore
import com.bangkit.booking_futsal.utils.AuthCallbackString
import com.bangkit.booking_futsal.utils.ViewModelFactory
import com.bangkit.booking_futsal.utils.showLoading

class CheckActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckBinding
    private val viewmodels: CheckViewmodels by viewModels()
    private lateinit var mainViewModel: MainViewmodels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = binding.edtCheck.text
        Log.e("id", id.toString())
        binding.btnCheck.setOnClickListener {
            setupViewModel(id.toString())
        }
    }

    private fun setupViewModel(id:String) {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(SettingPreferences.getInstance(dataStore), this)
        )[MainViewmodels::class.java]
        mainViewModel.getUser().observe(this) {
            val idFutsal = it.futsal_id
            viewmodels.checkOrder(idFutsal.toString(),id,object : AuthCallbackString {
                override fun onResponse(success: String, message: String) {
                    if (success == "true") {
                        binding.hasil.visibility = android.view.View.VISIBLE
                        binding.tvData.visibility = android.view.View.GONE
                    } else {
                        binding.hasil.visibility = android.view.View.GONE
                        binding.tvData.visibility = android.view.View.VISIBLE
                    }
                }
            })

        }
        viewmodels.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }

        viewmodels.itemCheck.observe(this) {
            with(binding){
                tvCheckNameUser.text = it.namaUser
                tvCheckNameFutsal.text = it.name
                tvCheckNameLapangan.text = it.namaLapangan
                tvCheckTanggal.text = it.tanggal
                tvCheckJam.text = it.jam
                tvCheckStatus.text = it.status
            }
            Log.e("TAG", "setupViewModel1: $it")
            Log.e("TAG", "setupViewModel2: ${ it.status }")
        }
    }
}