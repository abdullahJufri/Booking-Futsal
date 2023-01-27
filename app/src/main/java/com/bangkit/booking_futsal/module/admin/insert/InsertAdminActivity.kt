package com.bangkit.booking_futsal.module.admin.insert

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.data.remote.model.DashboardItem
import com.bangkit.booking_futsal.databinding.ActivityInsertAdminBinding
import com.bangkit.booking_futsal.databinding.ChoiceChipBinding
import com.bangkit.booking_futsal.module.admin.dashboard.DashboardViewmodels
import com.bangkit.booking_futsal.module.home.detail.DetailActivity
import com.bangkit.booking_futsal.module.splashscreen.dataStore
import com.bangkit.booking_futsal.utils.AuthCallbackString
import com.bangkit.booking_futsal.utils.ViewModelFactory
import com.google.android.material.chip.Chip
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import java.util.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class InsertAdminActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityInsertAdminBinding
    private lateinit var futsal: DashboardItem
    private var spinner: Spinner? = null
    private var resultLap: String? = null
    private var resultJam: String? = null
    private var resulDate: String? = null
    private var resulHarga: String? = "0"


    private val viewmodel: InsertAdminViewmodels by viewModels()
    private lateinit var mainViewModel: DashboardViewmodels
    private var id: String? = null

    val mCalendar: Calendar = Calendar.getInstance()
    private val format = "yyyy-MM-dd"
    private val sdf = java.text.SimpleDateFormat(format, Locale.getDefault())

    private var arrayAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        futsal = intent.getParcelableExtra(EXTRA_FUTSAL)!!
        viewmodel.getDataIntent(futsal)
        id = viewmodel.futsalsItem.id

        binding.tvDate.text = sdf.format(mCalendar.time)
        resulDate = sdf.format(mCalendar.time)
        settingBtn()

        binding.btnDate.setOnClickListener {
            dateDialog()
        }


        Log.e("TAG", "onCreate: ${futsal.hargaPagi}")

        setupViewModel()


    }

    private fun showDialog(OrderId: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Transaksi ID :")
        builder.setMessage(OrderId)
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

        }

//        builder.setNegativeButton(android.R.string.no) { dialog, which ->
//            Toast.makeText(applicationContext,
//                android.R.string.no, Toast.LENGTH_SHORT).show()
//        }
//
//        builder.setNeutralButton("Maybe") { dialog, which ->
//            Toast.makeText(applicationContext,
//                "Maybe", Toast.LENGTH_SHORT).show()
//        }
        builder.show()
    }

    private fun settingBtn() {
        binding.btnBayar.isEnabled = resulHarga != "0"
    }

    private fun setupViewModel() {

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(SettingPreferences.getInstance(dataStore), this)
        )[DashboardViewmodels::class.java]
        mainViewModel.getUser().observe(this) {
            val name = it.name
            val email = it.email
            val idUser = it.id
            val status = "offline"

            binding.btnBayar.setOnClickListener {


                viewmodel.showJam2(id.toString(), resultLap.toString(), resulDate.toString())



                viewmodel.itemJam2.observe(this) {


                    val a = it.map { it.jam?.take(2).toString() }.toString()
                    val b = resultJam.toString().take(2)
                    val c = a.contains(b)
                    Log.e("TAG", "setuB: $a")
                    Log.e("TAG", "setupMidtrans456: $a")

                    if (c) {
                        val a = it.map { it.jam?.take(2) }.contains(resultJam.toString().take(2))
                        Log.e("TAG", "setupMidtrans123: $a")
                        Log.e("Abdul", "if: $c")
                        Log.e("TAG", "run: ${it.map { it.jam?.take(2) }}")
                        Toast.makeText(this, "Jam Sudah Terbooking", Toast.LENGTH_SHORT).show()
                        binding.btnBayar.isEnabled = false
//                    finish()
                    } else {
                        val orderID = "${futsal.id}-${ System.currentTimeMillis()}"
                        viewmodel.insert(
                            id.toString(),
                            resultLap.toString(),
                            resulDate.toString(),
                            resultJam.toString(),
                            idUser,
                            resulHarga.toString(),
                            orderID,
                            status,
                            object :
                                AuthCallbackString {
                                override fun onResponse(success: String, message: String) {
                                    if (success == "true") {
                                        Toast.makeText(
                                            this@InsertAdminActivity,
                                            "Berhasil",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        showDialog(orderID)
                                        setupChip()
//                                        finish()
//                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(
                                            this@InsertAdminActivity,
                                            "Gagal",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()

                                        setupChip()

                                    }
                                }

                            })

                    }


                }
            }

        }


    }


    private fun setupChip() {
        val jam_buka = viewmodel.futsalsItem.jamBuka
        val jam_tutup = viewmodel.futsalsItem.jamTutup
        val nameList =
            arrayListOf(
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
                "23:00",
                "24:00"
            )


        viewmodel.showJam(id.toString(), resultLap.toString(), resulDate.toString())
        viewmodel.itemJam.observe(this) {
            binding.chipGroup.removeAllViews()
            for (name in nameList) {
                val chip = createChip(name)


                Log.e("jam", "jam ${it.map { it.jam }}")
//            val a = viewmodel.jamItem.jam


                if (name.take(2) < jam_buka.toString()
                        .take(2) || name.take(2) > jam_tutup.toString()
                        .take(2) || it.map { it.jam?.take(2) }.contains(name.take(2))
                ) {
                    binding.chipGroup.addView(chip)
                    chip.isEnabled = false
                } else {

                    binding.chipGroup.addView(chip)

                    chip.setOnClickListener {
                        if (name.take(2) <= "16") {
                            resulHarga = futsal.hargaPagi
                            binding.tvHarga.text = resulHarga
                            settingBtn()
                        } else {

                            resulHarga = futsal.hargaMalam
                            binding.tvHarga.text = resulHarga
                            settingBtn()
                        }
                        resultJam = name
                        Log.e("result2", "$resultJam")
                    }
                    chip.isEnabled = true
//                    binding.btnBayar.isEnabled = resulHarga != "0"

                }

                binding.chipGroup.isSingleSelection = true
            }
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


        val mDialog = DatePickerDialog(
            this,
            { _, mYear, mMonth, mDay ->
                mCalendar[Calendar.YEAR] = mYear
                mCalendar[Calendar.MONTH] = mMonth
                mCalendar[Calendar.DAY_OF_MONTH] = mDay
                resulDate = sdf.format(mCalendar.time)
                Log.e("resultDate", "$resulDate")


                binding.tvDate.text = sdf.format(mCalendar.time)
                resulHarga = "0"
                binding.tvHarga.text = resulHarga

                if (resultLap != null && resulDate != null) {
                    setupChip()
                }
            },
            mCalendar[Calendar.YEAR],
            mCalendar[Calendar.MONTH],
            mCalendar[Calendar.DAY_OF_MONTH]
        )

        mCalendar.set(year, month, day)
        mDialog.datePicker.minDate = mCalendar.timeInMillis

        mCalendar.set(year, month, day + 14)
        mDialog.datePicker.maxDate = mCalendar.timeInMillis

        mDialog.show()
        spinnerFutsal()


    }


    fun spinnerFutsal() {
        spinner = binding.spinner
        viewmodel.showListLapangan(id.toString())

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

        settingBtn()


    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        resulHarga = "0"
        binding.tvHarga.text = resulHarga
        settingBtn()
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

        if (resultLap != null && resulDate != null) {
            setupChip()
        }

        Log.e("resultlap", "$resultLap.")

        Toast.makeText(applicationContext, items, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(applicationContext, "Nothing Selected", Toast.LENGTH_SHORT).show()
    }


    companion object {
        const val EXTRA_FUTSAL = "extra_futsal"
    }
}