package com.bangkit.booking_futsal.module.home.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bangkit.booking_futsal.R
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
        with(binding){
            tvDtlName.text = viewmodel.futsalsItem.name
//            tvItemDesc.text = viewmodel.storyItem.description

            Glide.with(imgDtlPhotos)
                .load(viewmodel.futsalsItem.foto) // URL Avatar
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imgDtlPhotos)
        }
    }

    companion object {
        const val EXTRA_FUTSAL = "extra_futsal"
    }
}