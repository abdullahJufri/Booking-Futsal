package com.bangkit.booking_futsal.module.admin.check

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.booking_futsal.R
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.databinding.ActivityCheckBinding
import com.bangkit.booking_futsal.module.main.MainViewmodels
import com.bangkit.booking_futsal.module.splashscreen.dataStore
import com.bangkit.booking_futsal.utils.AuthCallbackString
import com.bangkit.booking_futsal.utils.ViewModelFactory
import com.bangkit.booking_futsal.utils.showLoading
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class CheckActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckBinding
    private val viewmodels: CheckViewmodels by viewModels()
    private lateinit var mainViewModel: MainViewmodels
    var scannedResult: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScan.setOnClickListener {
            run {
                IntentIntegrator(this@CheckActivity).initiateScan();
            }
        }
        val result = viewmodels.result2
        displayResult(result)



        setupViewModel()
        binding.btnCheck.setOnClickListener {
            val id = binding.edtCheck.text
            Log.e("resultabc1", id.toString())
            SetupCek(id.toString())
            Log.e("resultabc", result.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result != null){

            if(result.contents != null){
                scannedResult = result.contents
                binding.edtCheck.setText(scannedResult)
                viewmodels.scan(result.contents)
            } else {
                binding.edtCheck.setText("scan failed")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun displayResult(result: String) {
        binding.edtCheck.setText(result)
    }

//    override fun onSaveInstanceState(outState: Bundle?) {
//
//        outState?.putString("scannedResult", scannedResult)
//        super.onSaveInstanceState(outState)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
//        super.onRestoreInstanceState(savedInstanceState)
//
//        savedInstanceState?.let {
//            scannedResult = it.getString("scannedResult")
//            binding.txtValue.text = scannedResult
//        }
//    }



    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(SettingPreferences.getInstance(dataStore), this)
        )[MainViewmodels::class.java]
    }
   private fun SetupCek(id : String){
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
            if (binding.tvCheckStatus.text == "settlement"){
                with(binding.tvCheckStatus) {
                    setBackgroundColor(resources.getColor(com.midtrans.sdk.uikit.R.color.payment_status_success))
                    setTextColor(resources.getColor(com.bangkit.booking_futsal.R.color.white))
                }
            } else{
                with(binding.tvCheckStatus) {
                    setBackgroundColor(resources.getColor(com.midtrans.sdk.uikit.R.color.payment_status_failed))
                    setTextColor(resources.getColor(com.bangkit.booking_futsal.R.color.white))
                }
            }
            Log.e("TAG", "setupViewModel1: $it")
            Log.e("TAG", "setupViewModel2: ${ it.status }")
        }
    }
}