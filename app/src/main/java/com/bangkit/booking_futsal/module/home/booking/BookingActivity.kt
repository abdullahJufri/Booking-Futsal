package com.bangkit.booking_futsal.module.home.booking

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.booking_futsal.databinding.ActivityBookingBinding
import java.util.*

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)


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

        // Creating a simple calendar dialog.
        // It was 9 Aug 2021 when this program was developed.
        val mDialog = DatePickerDialog(this, { _, mYear, mMonth, mDay ->
            mCalendar[Calendar.YEAR] = mYear
            mCalendar[Calendar.MONTH] = mMonth
            mCalendar[Calendar.DAY_OF_MONTH] = mDay
            val format = "yyyy-MM-dd"
            val sdf = java.text.SimpleDateFormat(format, Locale.getDefault())
            binding.tvDate.text = sdf.format(mCalendar.time)
        }, mCalendar[Calendar.YEAR], mCalendar[Calendar.MONTH], mCalendar[Calendar.DAY_OF_MONTH])

        // Changing mCalendar date from current to
        // some random MIN day 15/08/2021 15 Aug 2021
        // If we want the same current day to be the MIN day,
        // then mCalendar is already set to today
//        // and the below code will be unnecessary
//        val minDay = 15
//        val minMonth = 8
//        val minYear = 2021
        mCalendar.set(year, month, day)
        mDialog.datePicker.minDate = mCalendar.timeInMillis

        // Changing mCalendar date from current to
        // some random MAX day 20/08/2021 20 Aug 2021
//        val maxDay = 20
//        val maxMonth = 8
//        val maxYear = 2021
        mCalendar.set(year, month, day + 14)
        mDialog.datePicker.maxDate = mCalendar.timeInMillis

        // Display the calendar dialog
        mDialog.show()
    }


//    open fun setMinDate(minDate: Long): Unit {
//        binding.datePicker.minDate = minDate
//        NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
//            val calendar = Calendar.getInstance()
//            calendar.set(Calendar.YEAR, newVal)
//            calendar.set(Calendar.MONTH, binding.datePicker.month)
//            calendar.set(Calendar.DAY_OF_MONTH, binding.datePicker.dayOfMonth)
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
//            calendar.timeZone = TimeZone.getDefault()
//            val time = calendar.timeInMillis
//            if (time < minDate) {
//                binding.datePicker.updateDate(
//                    calendar.get(Calendar.YEAR),
//                    calendar.get(Calendar.MONTH),
//                    calendar.get(Calendar.DAY_OF_MONTH)
//                )
//            }
//        }
//    }


    companion object {
        const val EXTRA_FUTSAL = "extra_futsal"
    }
}

