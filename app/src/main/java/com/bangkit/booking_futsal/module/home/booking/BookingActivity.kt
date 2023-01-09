package com.bangkit.booking_futsal.module.home.booking

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
    private var resultLap: String? = null
    private var resultJam: String? = null
    private var resulDate: String? = null
    private var resulHarga: String? = null


    private val viewmodel: BookingViewmodels by viewModels()
    private var id: String? = null

    val mCalendar: Calendar = Calendar.getInstance()
    private val format = "yyyy-MM-dd"
    private val sdf = java.text.SimpleDateFormat(format, Locale.getDefault())


    private var arrayAdapter: ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        futsal = intent.getParcelableExtra(DetailActivity.EXTRA_FUTSAL)!!


        binding.tvDate.text = sdf.format(mCalendar.time)
        resulDate = sdf.format(mCalendar.time)
        Log.e("resultDate", "$resulDate")
        binding.btnDate.setOnClickListener {
            dateDialog()
        }
        spinnerFutsal()
        Log.e("TAG", "onCreate: ${futsal.hargaPagi}")
        setupChip()


    }


    private fun setupChip() {
        val jam_buka = viewmodel.futsalsItem.jamBuka
        val jam_tutup = viewmodel.futsalsItem.jamTutup
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
                    if (name.take(2) <= "16") {
                        binding.tvHarga.text = futsal.hargaPagi
                        resulDate = futsal.hargaPagi
                    } else {
                        binding.tvHarga.text = futsal.hargaMalam
                        resulDate = futsal.hargaMalam
                    }
                    resultJam = name
                    Log.e("result2", "$resultJam")
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


        val mDialog = DatePickerDialog(this, { _, mYear, mMonth, mDay ->
            mCalendar[Calendar.YEAR] = mYear
            mCalendar[Calendar.MONTH] = mMonth
            mCalendar[Calendar.DAY_OF_MONTH] = mDay
            resulDate = sdf.format(mCalendar.time)
            Log.e("resultDate", "$resulDate")


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

        val items: String = parent?.getItemAtPosition(position).toString()
        Log.e("TAG", "onItemSelected: ${parent?.count}")
        when (parent?.count) {
            1 -> if (items == "Lapangan 1") {
                resultLap = "1"
            }
            2 -> when (items) {
                "Lapangan 1" -> resultLap = "2"
                "Lapangan 2" -> resultLap = "3"
            }
            3 -> when (items) {
                "Lapangan 1" -> resultLap = "4"
                "Lapangan 2" -> resultLap = "5"
                "Lapangan 3" -> resultLap = "6"
            }

        }
        Log.e("result1", "$resultLap.")
        Toast.makeText(applicationContext, items, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(applicationContext, "Nothing Selected", Toast.LENGTH_SHORT).show()
    }


    companion object {
        const val EXTRA_FUTSAL = "extra_futsal"
    }
}


