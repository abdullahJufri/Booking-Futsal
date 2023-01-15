package com.bangkit.booking_futsal.module.admin.edit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.booking_futsal.data.remote.model.DashboardItem
import com.bangkit.booking_futsal.databinding.ActivityEditFutsalBinding
import com.bangkit.booking_futsal.utils.AuthCallbackString

class EditFutsalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditFutsalBinding
    private lateinit var futsal: DashboardItem

    private val viewmodel: EditFutsalViewmodels by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFutsalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        futsal = intent.getParcelableExtra(EXTRA_FUTSAL)!!
        viewmodel.getDataIntent(futsal)


        with(binding) {
            tvEditNama.text = futsal.name
            edtAlamat.setText(futsal.alamatLapangan)
            edtJmlLap.setText(futsal.jumlahLapangan)
            edtHargaPagi.setText(futsal.hargaPagi)
            edtHargaMalam.setText(futsal.hargaMalam)
        }



        binding.button.setOnClickListener {
            val idPengelola = futsal.idPengelola
            val alamat = binding.edtAlamat.text.toString()
            val jmlLap = binding.edtJmlLap.text.toString()
            val hargaPagi = binding.edtHargaPagi.text.toString()
            val hargaMalam = binding.edtHargaMalam.text.toString()
            Log.e("TAG", "onCreate: $idPengelola $alamat $jmlLap $hargaPagi $hargaMalam")
            viewmodel.update(idPengelola!! , alamat, jmlLap, hargaPagi, hargaMalam, object :
                AuthCallbackString {
                override fun onResponse(success: String, message: String) {
                    Toast.makeText(this@EditFutsalActivity, message, Toast.LENGTH_SHORT).show()
                    finish()

                }
            })

        }
    }


    companion object {
        const val EXTRA_FUTSAL = "extra_futsal"
    }
}