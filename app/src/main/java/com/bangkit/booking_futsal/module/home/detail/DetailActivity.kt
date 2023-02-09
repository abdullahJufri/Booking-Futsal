package com.bangkit.booking_futsal.module.home.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bangkit.booking_futsal.R
import com.bangkit.booking_futsal.data.remote.api.ApiConfig
import com.bangkit.booking_futsal.data.remote.model.FutsalsItem
import com.bangkit.booking_futsal.databinding.ActivityDetailBinding
import com.bangkit.booking_futsal.module.home.booking.BookingActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewmodel: DetailViiewmodels by viewModels()
    private lateinit var futsal: FutsalsItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        futsal = intent.getParcelableExtra(EXTRA_FUTSAL)!!
        viewmodel.setDetailStory(futsal)
        displayResult()

        binding.btnBooking.setOnClickListener {
//            val intent = Intent(this, BookingActivity::class.java)
//            startActivity(intent)
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra(BookingActivity.EXTRA_FUTSAL, futsal)
            startActivity(intent)
        }

    }

    private fun displayResult() {
        val map = Uri.parse(viewmodel.futsalsItem.maps)
        val intent = Intent(Intent.ACTION_VIEW, map)
        intent.setPackage("com.google.android.apps.maps")
        binding.btnMap.setOnClickListener {
            startActivity(intent)
        }
        with(binding){
            tvDtlName.text = futsal.name
            tvDtlLap.text = futsal.jumlahLapangan

            tvDtlAlamat.text = futsal.alamatLapangan

            tvDtlJamBuka.text = futsal.jamBuka
            tvDtlJamTutup.text = futsal.jamTutup
//            tvItemDesc.text = viewmodel.storyItem.description

            val urlPhoto = "${ApiConfig.baseUrl}${futsal.foto}"
            Glide.with(imgDtlPhotos)
                .load(urlPhoto) // URL Avatar
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imgDtlPhotos)
        }
    }

    companion object {
        const val EXTRA_FUTSAL = "extra_futsal"
    }
}