package com.bangkit.booking_futsal.module.admin.transaksi

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.data.remote.model.DashboardItem
import com.bangkit.booking_futsal.databinding.ActivityTransaksiBinding
import com.bangkit.booking_futsal.module.admin.dashboard.DashboardViewmodels
import com.bangkit.booking_futsal.module.admin.insert.InsertAdminActivity
import com.bangkit.booking_futsal.module.splashscreen.dataStore
import com.bangkit.booking_futsal.utils.ViewModelFactory
import java.util.*



private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class TransaksiActivity : AppCompatActivity() {
private lateinit var binding: ActivityTransaksiBinding
    private lateinit var futsal: DashboardItem

    private val viewmodel: TransaksiViewmodels by viewModels()
    private lateinit var mainViewModel: DashboardViewmodels
    private lateinit var adapter: TransaksiAdapter

    private var resulDate: String? = null
    val mCalendar: Calendar = Calendar.getInstance()
    private val format = "yyyy-MM-dd"
    private val sdf = java.text.SimpleDateFormat(format, Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewmodel()
        futsal = intent.getParcelableExtra(InsertAdminActivity.EXTRA_FUTSAL)!!
        viewmodel.getDataIntent(futsal)

        binding.btnDate.setOnClickListener {
            dateDialog()
        }


    }

    private fun setupViewmodel(){
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(SettingPreferences.getInstance(dataStore), this)
        )[DashboardViewmodels::class.java]
    }

    private fun showRecyclerView() {
        binding.rvTransaksi.layoutManager = LinearLayoutManager(this)
        adapter = TransaksiAdapter()

        binding.rvTransaksi.adapter = adapter
    }

    private fun setListHistory(resultDate : String) {
        viewmodel.showListTransaksi(futsal.id.toString(), resultDate)

        viewmodel.itemTransaksi.observe(this) {
            val jumlahTransaksi = it.size
            val pemasukan = it.sumOf { it.harga!!.toDouble() }


            if (it.isEmpty()) {
//                binding.clTransaksi.visibility = View.GONE
                binding.tvJmlh.text = "0"
                binding.tvTotalPemasukan.text = "0"
            } else {
//                binding.clTransaksi.visibility = View.VISIBLE
                binding.tvJmlh.text = jumlahTransaksi.toString()
                binding.tvTotalPemasukan.text = pemasukan.toString()

            }
            adapter.setListHistory(it)
        }
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

//                if(resulDate!= null){
                    setListHistory(resulDate.toString())
                    showRecyclerView()
//                }
                binding.tvDate.text = sdf.format(mCalendar.time)

            },
            mCalendar[Calendar.YEAR],
            mCalendar[Calendar.MONTH],
            mCalendar[Calendar.DAY_OF_MONTH]
        )

        mCalendar.set(year, month, day)
        mDialog.datePicker.maxDate = mCalendar.timeInMillis

//        mCalendar.set(year, month, day + 14)
//        mDialog.datePicker.maxDate = mCalendar.timeInMillis

        mDialog.show()
//        setListHistory(resulDate.toString())
//        showRecyclerView()

//        viewmodel.showListTransaksi(futsal.id.toString(), resulDate!!)
//        setListHistory(resulDate.toString())
//        showRecyclerView()


    }
    companion object {
        const val EXTRA_FUTSAL = "extra_futsal"
    }
}