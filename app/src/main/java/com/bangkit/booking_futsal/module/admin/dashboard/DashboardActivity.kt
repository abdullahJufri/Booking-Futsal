package com.bangkit.booking_futsal.module.admin.dashboard

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.bangkit.booking_futsal.databinding.ActivityDashboardBinding
import com.bangkit.booking_futsal.module.admin.check.CheckActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCekBooking.setOnClickListener{
            intent = android.content.Intent(this, CheckActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Cek Booking", Toast.LENGTH_SHORT).show()
        }
    }
}