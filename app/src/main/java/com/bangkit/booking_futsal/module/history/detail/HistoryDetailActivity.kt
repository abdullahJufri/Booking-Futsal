package com.bangkit.booking_futsal.module.history.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.booking_futsal.R
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
            binding.tvPembayaran.text = it.paymentType
            if (status.toString() == "null") {
                with(binding.tvStatus) {
                    text = "INVALID"
                    setBackgroundColor(resources.getColor(com.midtrans.sdk.uikit.R.color.payment_status_failed))
                    setTextColor(resources.getColor(R.color.white))

                }

            } else {
                if (it.transactionStatus == status) {
                    if (status == "settlement") {
                        with(binding.tvStatus) {
                            text = it.transactionStatus
                            setBackgroundColor(resources.getColor(com.midtrans.sdk.uikit.R.color.payment_status_success))
                            setTextColor(resources.getColor(R.color.white))
                        }
                    } else {
                        with(binding.tvStatus) {
                            text = it.transactionStatus
                            if (text == "settlement"){
                                setBackgroundColor(resources.getColor(com.midtrans.sdk.uikit.R.color.payment_status_success))
                                setTextColor(resources.getColor(R.color.white))
                            } else{
                                setBackgroundColor(resources.getColor(com.midtrans.sdk.uikit.R.color.payment_status_failed))
                                setTextColor(resources.getColor(R.color.white))
                            }

                        }

                    }
                } else {
                    viewmodel.update(orderId.toString(), it.transactionStatus.toString(), object :
                        AuthCallbackString {
                        override fun onResponse(success: String, message: String) {
                            with(binding.tvStatus) {
                                text = it.transactionStatus
                                setBackgroundColor(resources.getColor(com.midtrans.sdk.uikit.R.color.payment_status_failed))
                                setTextColor(resources.getColor(R.color.white))
                            }

                        }

                    })
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