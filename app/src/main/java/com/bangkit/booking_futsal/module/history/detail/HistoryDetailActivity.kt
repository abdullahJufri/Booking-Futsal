package com.bangkit.booking_futsal.module.history.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.map
import com.bangkit.booking_futsal.data.remote.model.HistoryItem
import com.bangkit.booking_futsal.databinding.ActivityHistoryDetailBinding
import com.bangkit.booking_futsal.utils.AuthCallbackString

class HistoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryDetailBinding
    private val viewmodel: HistoryDetailViewmodels by viewModels()
    private lateinit var history: HistoryItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        history = intent.getParcelableExtra(EXTRA_HISTORY)!!
        viewmodel.setDetailHistory(history)
        displayResult()
    }

    private fun displayResult() {
        viewmodel.isLoading.observe(this) {
            binding.progressBar.visibility =
                if (it) android.view.View.VISIBLE else android.view.View.GONE
        }
        val orderId = viewmodel.hystoriesItem.orderId
        val status = viewmodel.hystoriesItem.status
        viewmodel.getMidtrans(orderId.toString())
        viewmodel.itemMidtrans.observe(this) {
            Log.d("TAG", "displayResult: ${it.transactionStatus}")
            if (status.toString() == "null") {
                binding.tvStatus.text = "INVALID"
            } else{
                if (it.transactionStatus == status){
                    binding.tvStatus.text = it.transactionStatus
                } else{
                    viewmodel.update(orderId.toString(),it.transactionStatus.toString(),object :
                        AuthCallbackString{
                        override fun onResponse(success: String, message: String) {
                            binding.tvStatus.text = it.transactionStatus
                        }

                    })
                }
            }

        }


        with(binding) {
            tvName.text = viewmodel.hystoriesItem.name
            tvJenis.text = viewmodel.hystoriesItem.nama_lapangan
            tvTanggal.text = viewmodel.hystoriesItem.tanggal
            tvJam.text = viewmodel.hystoriesItem.jam
            tvIhistoryHarga.text = viewmodel.hystoriesItem.harga
            tvOrderId.text = viewmodel.hystoriesItem.orderId.toString()

//            tvStatus.text = viewmodel.hystoriesItem.status
        }
    }

//    private fun displayResult() {
//        viewmodel.isLoading.observe(this) {
//            binding.progressBar.visibility =
//                if (it) android.view.View.VISIBLE else android.view.View.GONE
//        }
//        val a = viewmodel.hystoriesItem.orderId
//        val status = viewmodel.hystoriesItem.status
//        viewmodel.getMidtrans(a.toString())
//        viewmodel.itemMidtrans.observe(this) {
//            Log.d("TAG", "displayResult: ${it.transactionStatus}")
//            if (it.transactionStatus == status){
//                binding.tvStatus.text = it.transactionStatus
//            } else{
//                binding.tvStatus.text = "belom"
//            }
//        }
//
//
//        with(binding) {
//            tvName.text = viewmodel.hystoriesItem.name
////            tvStatus.text = viewmodel.hystoriesItem.status
//        }
//    }

    companion object {
        const val EXTRA_HISTORY = "extra_history"
    }
}