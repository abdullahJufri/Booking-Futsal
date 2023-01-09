package com.bangkit.booking_futsal.module.home.booking

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.booking_futsal.R
import com.bangkit.booking_futsal.data.remote.model.FutsalsItem
import com.bangkit.booking_futsal.databinding.ActivityBookingBinding
import com.bangkit.booking_futsal.databinding.ChoiceChipBinding
import com.bangkit.booking_futsal.module.home.detail.DetailActivity
import com.google.android.material.chip.Chip
import java.util.*

class BookingActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityBookingBinding
    private lateinit var futsal: FutsalsItem
    private var spinner: Spinner? = null
    private var result: TextView? = null
    private var result2: TextView? = null

    private val viewmodel: BookingViewmodels by viewModels()
    private var id: String? = null


    private var arrayAdapter: ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        futsal = intent.getParcelableExtra(DetailActivity.EXTRA_FUTSAL)!!



        binding.btnDate.setOnClickListener {
            dateDialog()
        }
        spinnerFutsal()
        result = binding.tvPilih
        result2 = binding.tvTest
        Log.e("TAG", "onCreate: ${futsal.hargaPagi}")
        setupChip()



//        for (i in 0 until 24) {
//            var chip = Chip(this)
//            binding.chipGroup.addView(chip)
//            with(binding){
//                chip0.isEnabled = false
//
//            }
//
//        }
    }


    private fun setupChip() {
        var jam_buka = viewmodel.futsalsItem.jamBuka
        var jam_tutup = viewmodel.futsalsItem.jamTutup
        val nameList =
            arrayListOf(
                "00:00",
                "01:00",
                "02:00",
                "03:00",
                "04:00",
                "05:00",
                "06:00",
                "07:00",
                "08:00",
                "09:00",
                "10:00",
                "11:00",
                "12:00",
                "13:00",
                "14:00",
                "15:00",
                "16:00",
                "17:00",
                "18:00",
                "19:00",
                "20:00",
                "21:00",
                "22:00",
                "23:00"
            )
        for (name in nameList) {
            val chip = createChip(name)
            if (name.take(2) < jam_buka.toString().take(2) || name.take(2) > jam_tutup.toString()
                    .take(2)
            ) {
                binding.chipGroup.addView(chip)
                chip.isEnabled = false
            } else {
                binding.chipGroup.addView(chip)
                chip.setOnClickListener {
                    result2?.text = name
                }



                chip.isEnabled = true
            }
            binding.chipGroup.isSingleSelection = true


        }
    }

    private fun createChip(label: String): Chip {
        val chip = ChoiceChipBinding.inflate(layoutInflater).root
        chip.text = label
        return chip
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

    fun spinnerFutsal() {
        viewmodel.setDetailStory(futsal)
        id = viewmodel.futsalsItem.id
        spinner = binding.spinner
        viewmodel.showListFutsal(id.toString())

        viewmodel.itemLapangan.observe(this) {
            arrayAdapter = ArrayAdapter(
                applicationContext,
                android.R.layout.simple_spinner_item,
                it.map { it.label },
            )
            Log.e("TAG", "spinnerFutsal2: ${it}")
            spinner?.adapter = arrayAdapter
            spinner?.onItemSelectedListener = this
        }
        spinner?.adapter = arrayAdapter
        spinner?.onItemSelectedListener = this

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        var items: String = parent?.getItemAtPosition(position).toString()
        Log.e("TAG", "onItemSelected: ${parent?.count}")
        when (parent?.count) {
            1 -> if (items == "Lapangan 1") {
                result?.text = "1"
            }
            2 -> when (items) {
                "Lapangan 1" -> result?.text = "2"
                "Lapangan 2" -> result?.text = "3"
            }
            3 -> when (items) {
                "Lapangan 1" -> result?.text = "4"
                "Lapangan 2" -> result?.text = "5"
                "Lapangan 3" -> result?.text = "6"
            }
        }
        Toast.makeText(applicationContext, items, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(applicationContext, "Nothing Selected", Toast.LENGTH_SHORT).show()
    }


    companion object {
        const val EXTRA_FUTSAL = "extra_futsal"
    }
}


