package com.bangkit.booking_futsal.module.admin.check

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.booking_futsal.databinding.ActivityCheckBinding
import com.bangkit.booking_futsal.utils.showLoading

class CheckActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckBinding
    private val viewmodels: CheckViewmodels by viewModels()

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

        viewmodels.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }
        viewmodels.checkOrder(id)
        viewmodels.itemCheck.observe(this) {
            binding.tvTest.text = it.status
            Log.e("TAG", "setupViewModel1: $it")
            Log.e("TAG", "setupViewModel2: ${ it.status }")
        }
    }
}