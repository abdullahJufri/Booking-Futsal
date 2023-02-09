package com.bangkit.booking_futsal.module.history.detail

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.booking_futsal.R
import com.bangkit.booking_futsal.data.remote.model.HistoryItem
import com.bangkit.booking_futsal.databinding.ActivityHistoryDetailBinding
import com.bangkit.booking_futsal.utils.AuthCallbackString
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer

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
                val transactionId = viewmodel.hystoriesItem.orderId
                if (it.transactionStatus == status) {
                    if (status == "settlement") {

                        with(binding.tvStatus) {
                            text = it.transactionStatus
                            setBackgroundColor(resources.getColor(com.midtrans.sdk.uikit.R.color.payment_status_success))
                            setTextColor(resources.getColor(R.color.white))
                            displayBitmap(transactionId.toString())
                        }
                    } else {
                        with(binding.tvStatus) {
                            text = it.transactionStatus
                            setBackgroundColor(resources.getColor(com.midtrans.sdk.uikit.R.color.payment_status_failed))
                            setTextColor(resources.getColor(R.color.white))
                        }

                    }
                } else {
                    viewmodel.update(orderId.toString(), it.transactionStatus.toString(), object :
                        AuthCallbackString {
                        override fun onResponse(success: String, message: String) {
                            if (it.transactionStatus == "settlement") {
                                with(binding.tvStatus) {
                                    text = it.transactionStatus
                                    setBackgroundColor(resources.getColor(com.midtrans.sdk.uikit.R.color.payment_status_success))
                                    setTextColor(resources.getColor(R.color.white))
                                    displayBitmap(transactionId.toString())
                                }
                            } else {
                                with(binding.tvStatus) {
                                    text = it.transactionStatus
                                    setBackgroundColor(resources.getColor(com.midtrans.sdk.uikit.R.color.payment_status_failed))
                                    setTextColor(resources.getColor(R.color.white))
                                }

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

    private fun createBarcodeBitmap(
        barcodeValue: String,
        @ColorInt barcodeColor: Int,
        @ColorInt backgroundColor: Int,
        widthPixels: Int,
        heightPixels: Int
    ): Bitmap {
        val bitMatrix = Code128Writer().encode(
            barcodeValue,
            BarcodeFormat.CODE_128,
            widthPixels,
            heightPixels
        )

        val pixels = IntArray(bitMatrix.width * bitMatrix.height)
        for (y in 0 until bitMatrix.height) {
            val offset = y * bitMatrix.width
            for (x in 0 until bitMatrix.width) {
                pixels[offset + x] =
                    if (bitMatrix.get(x, y)) barcodeColor else backgroundColor
            }
        }

        val bitmap = Bitmap.createBitmap(
            bitMatrix.width,
            bitMatrix.height,
            Bitmap.Config.ARGB_8888
        )
        bitmap.setPixels(
            pixels,
            0,
            bitMatrix.width,
            0,
            0,
            bitMatrix.width,
            bitMatrix.height
        )
        return bitmap
    }

    private fun displayBitmap(value: String) {
        val widthPixels = resources.getDimensionPixelSize(R.dimen.width_barcode)
        val heightPixels = resources.getDimensionPixelSize(R.dimen.height_barcode)

        binding.imgBarcode.setImageBitmap(
            createBarcodeBitmap(
                barcodeValue = value,
                barcodeColor = getColor(R.color.black),
                backgroundColor = getColor(android.R.color.white),
                widthPixels = widthPixels,
                heightPixels = heightPixels
            )
        )
        binding.textBarcodeNumber.text = value
    }

    companion object {
        const val EXTRA_HISTORY = "extra_history"
    }
}