package com.bangkit.booking_futsal.module.home.booking

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.booking_futsal.data.remote.model.FutsalsItem
import com.bangkit.booking_futsal.databinding.ActivityBookingBinding
import com.bangkit.booking_futsal.module.home.detail.DetailActivity
import java.util.*

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding
    private lateinit var futsal: FutsalsItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        futsal = intent.getParcelableExtra(DetailActivity.EXTRA_FUTSAL)!!


        binding.btnDate.setOnClickListener {
            dateDialog()
        }


    }

    fun dateDialog() {
        val todayDate: Calendar = Calendar.getInstance()
        val year = todayDate.get(Calendar.YEAR)
        val month = todayDate.get(Calendar.MONTH)
        val day = todayDate.get(Calendar.DAY_OF_MONTH)

        val mCalendar = Calendar.getInstance()

        val mDialog = DatePickerDialog(this, { _, mYear, mMonth, mDay ->
            mCalendar[Calendar.YEAR] = mYear
            mCalendar[Calendar.MONTH] = mMonth
            mCalendar[Calendar.DAY_OF_MONTH] = mDay
            val format = "yyyy-MM-dd"
            val sdf = java.text.SimpleDateFormat(format, Locale.getDefault())
            binding.tvDate.text = sdf.format(mCalendar.time)
        }, mCalendar[Calendar.YEAR], mCalendar[Calendar.MONTH], mCalendar[Calendar.DAY_OF_MONTH])

        mCalendar.set(year, month, day)
        mDialog.datePicker.minDate = mCalendar.timeInMillis

        mCalendar.set(year, month, day + 14)
        mDialog.datePicker.maxDate = mCalendar.timeInMillis

        mDialog.show()
    }




    companion object {
        const val EXTRA_FUTSAL = "extra_futsal"
    }
}

