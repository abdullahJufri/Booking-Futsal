package com.bangkit.booking_futsal.module.home.booking

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import com.bangkit.booking_futsal.data.remote.model.FutsalsItem
import com.bangkit.booking_futsal.data.remote.model.SpinnerItems
import com.bangkit.booking_futsal.databinding.ActivityBookingBinding
import com.bangkit.booking_futsal.module.home.detail.DetailActivity
import com.bangkit.booking_futsal.utils.DiffCallback2
import java.util.*

class BookingActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityBookingBinding
    private lateinit var futsal: FutsalsItem
    private var spinner: Spinner? = null


    private val viewmodel: BookingViewmodels by viewModels()
    private var id: String? = null
    private val listLapangan = ArrayList<SpinnerItems>()

//    private val listFutsal2 = List<FutsalsItem>()

    private var arrayAdapter: ArrayAdapter<String>? = null
    private var arrayListAdapter: ListAdapter? = null


    private var itemList = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        futsal = intent.getParcelableExtra(DetailActivity.EXTRA_FUTSAL)!!



        binding.btnDate.setOnClickListener {
            dateDialog()
        }
        spinnerFutsal()


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

        arrayAdapter =
            ArrayAdapter(
                applicationContext,
                android.R.layout.simple_spinner_item,
                listLapangan.map { it.label })
        Log.e("TAG", "spinnerFutsal: ${listLapangan.map { it.label }}")
//        arrayAdapter =
//            ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, itemList)
//        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        viewmodel.itemFutsal.observe(this) {
//            setListStory(it).toString()
            arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, it.map { it.label })
//            arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            Log.e("TAG", "spinnerFutsal2: ${it}")
            spinner?.adapter = arrayAdapter
            spinner?.onItemSelectedListener = this
        }

        spinner?.adapter = arrayAdapter
        spinner?.onItemSelectedListener = this

    }

    fun setListStory(itemLapangn: List<SpinnerItems>) {
        val diffCallback = DiffCallback2(this.listLapangan, itemLapangn)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listLapangan.clear()
        this.listLapangan.addAll(itemLapangn)

    }


    fun spinnerFutsal2() {
        spinner = binding.spinner
        arrayAdapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, itemList)
//        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = arrayAdapter
        spinner?.onItemSelectedListener = this

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var items: String = parent?.getItemAtPosition(position).toString()
        Toast.makeText(applicationContext, items, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(applicationContext, "Nothing Selected", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_FUTSAL = "extra_futsal"
    }
}


