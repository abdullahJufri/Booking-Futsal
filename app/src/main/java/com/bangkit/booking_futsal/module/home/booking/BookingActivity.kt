package com.bangkit.booking_futsal.module.home.booking

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
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
import com.bangkit.booking_futsal.data.remote.model.FutsalsItem
import com.bangkit.booking_futsal.data.remote.model.ScheduleItem
import com.bangkit.booking_futsal.databinding.ActivityBookingBinding
import com.bangkit.booking_futsal.databinding.ChoiceChipBinding
import com.bangkit.booking_futsal.module.home.detail.DetailActivity
import com.bangkit.booking_futsal.module.main.MainActivity
import com.bangkit.booking_futsal.module.main.MainViewmodels
import com.bangkit.booking_futsal.module.splashscreen.dataStore
import com.bangkit.booking_futsal.utils.AuthCallbackString
import com.bangkit.booking_futsal.utils.ViewModelFactory
import com.google.android.material.chip.Chip
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import java.util.*


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class BookingActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityBookingBinding
    private lateinit var futsal: FutsalsItem
    private lateinit var schedule: ScheduleItem
    private var spinner: Spinner? = null
    private var resultLap: String? = null
    private var resultJam: String? = null
    private var resulDate: String? = null
    private var resulHarga: String? = "0"


    private val viewmodel: BookingViewmodels by viewModels()
    private lateinit var mainViewModel: MainViewmodels
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

        viewmodel.setDetailStory(futsal)
        id = viewmodel.futsalsItem.id


        binding.tvDate.text = sdf.format(mCalendar.time)
        resulDate = sdf.format(mCalendar.time)
        Log.e("resultDate", "$resulDate")
        settingBtn()

        binding.btnDate.setOnClickListener {
            dateDialog()
        }


        Log.e("TAG", "onCreate: ${futsal.hargaPagi}")

        setupViewModel()


    }

    private fun settingBtn() {
        binding.btnBayar.isEnabled = resulHarga != "0"
    }

    private fun setupViewModel() {

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(SettingPreferences.getInstance(dataStore), this)
        )[MainViewmodels::class.java]
        mainViewModel.getUser().observe(this) {
            val name = it.name
            val email = it.email
            val idUser = it.id

            setupMidtrans(name, email, idUser)
        }


    }

    fun setupMidtrans(name: String, email: String, idUser: String) {

        val orderID = "${futsal.id}-${ System.currentTimeMillis()}"
        SdkUIFlowBuilder.init()
            .setClientKey("SB-Mid-client-9a98NH6qLZxBZ3py")
            .setContext(applicationContext)
            .setTransactionFinishedCallback(TransactionFinishedCallback {

                    result ->
                var status: String? = null
                if (result.status.equals("pending", true)) {
                    status = "pending"
                    Toast.makeText(this, "pending", Toast.LENGTH_SHORT).show()
                } else if (result.status.equals("success", true)) {
                    status = "success"
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                } else if (result.status.equals("fail", true)) {
                    status = "Failed"
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                } else if (result.status.equals("cancel", true)) {
                    status = "cancel"
                    Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show()

                }
                viewmodel.insert(
                    id.toString(),
                    resultLap.toString(),
                    resulDate.toString(),
                    resultJam.toString(),
                    idUser,
                    resulHarga.toString(),
                    orderID,
                    status.toString(),
                    object :
                        AuthCallbackString {
                        override fun onResponse(success: String, message: String) {
                            if (success == "true") {
                                Toast.makeText(this@BookingActivity, "Berhasil", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(this@BookingActivity, "Gagal", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                    })
                val inten = Intent(this, MainActivity::class.java)
                startActivity(inten)
            })
            .setMerchantBaseUrl("http://midtrans.djstudio.tech/test.php/charge/")
            .enableLog(true)
            .setLanguage("id")
            .buildSDK()

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
                    Log.e("Abdul", "else: $c")
                    val transactionRequest = resulHarga?.toDouble()?.let { it1 ->
                        TransactionRequest(
                            orderID,
                            it1
                        )
                    }
                    val detail =
                        resulHarga?.toDouble()
                            ?.let { it1 -> ItemDetails(futsal.name, it1, 1, resultLap.toString()) }

                    val itemDetails = ArrayList<ItemDetails>()
                    if (detail != null) {
                        itemDetails.add(detail)
                    }
                    if (transactionRequest != null) {
                        uiKitDetail(transactionRequest, name, email)
                    }
                    Log.e("hargaFalse", resulHarga.toString())
                    transactionRequest?.itemDetails = itemDetails
                    MidtransSDK.getInstance().transactionRequest = transactionRequest
                    MidtransSDK.getInstance().startPaymentUiFlow(this)

                }

//            val transactionRequest = TransactionRequest()


            }
        }
    }


    fun uiKitDetail(transactionRequest: TransactionRequest, name: String, email: String) {
        val customerDetails = CustomerDetails()
        customerDetails.customerIdentifier = name
        customerDetails.phone = "08123456789"
        customerDetails.firstName = name
        customerDetails.lastName = "-"
        customerDetails.email = email

        val shippingAddress = ShippingAddress()
        shippingAddress.address = "-"
        shippingAddress.city = "-"
        shippingAddress.postalCode = "-"
        customerDetails.shippingAddress = shippingAddress

        val billingAddress = BillingAddress()
        billingAddress.address = "-"
        billingAddress.city = "-"
        billingAddress.postalCode = ""
        customerDetails.billingAddress = billingAddress

        transactionRequest.customerDetails = customerDetails
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


