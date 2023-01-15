package com.bangkit.booking_futsal.module.admin.dashboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.data.remote.model.Auth
import com.bangkit.booking_futsal.data.remote.model.DashboardItem
import com.bangkit.booking_futsal.databinding.ActivityDashboardBinding
import com.bangkit.booking_futsal.module.admin.check.CheckActivity
import com.bangkit.booking_futsal.module.admin.insert.InsertAdminActivity
import com.bangkit.booking_futsal.module.admin.transaksi.TransaksiActivity
import com.bangkit.booking_futsal.module.auth.AuthActivity
import com.bangkit.booking_futsal.module.home.detail.DetailActivity
import com.bangkit.booking_futsal.module.splashscreen.dataStore
import com.bangkit.booking_futsal.utils.ViewModelFactory
import com.bangkit.booking_futsal.utils.showLoading

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var viewmodels: DashboardViewmodels
//    private val listFutsal = ArrayList<DashboardItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewmodel()
        setupCheck()
//        setupInsert()
        setupLogout()



    }

    fun setupLogout(){
        binding.btnLogout.setOnClickListener {
            viewmodels.logout()
            intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Cek Booking", Toast.LENGTH_SHORT).show()
        }
    }
    fun setupTransaksi(futsal: DashboardItem){
        binding.btnTransaksi.setOnClickListener {
            intent = android.content.Intent(this, TransaksiActivity::class.java)
            intent.putExtra(TransaksiActivity.EXTRA_FUTSAL, futsal)
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())

//            val intent = Intent(this, InsertAdminActivity::class.java)
//            startActivity(intent)
            Toast.makeText(this, "Transaksi", Toast.LENGTH_SHORT).show()
        }
    }

    fun setupInsert(futsal: DashboardItem){
        binding.btnInsertBooking.setOnClickListener {
            intent = android.content.Intent(this, InsertAdminActivity::class.java)
            intent.putExtra(InsertAdminActivity.EXTRA_FUTSAL, futsal)
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())

//            val intent = Intent(this, InsertAdminActivity::class.java)
//            startActivity(intent)
            Toast.makeText(this, "Cek Booking", Toast.LENGTH_SHORT).show()
        }
    }


    fun setupCheck(){
        binding.btnCekBooking.setOnClickListener {
            intent = Intent(this, CheckActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Cek Booking", Toast.LENGTH_SHORT).show()
        }
    }

    fun setupViewmodel() {
        viewmodels = ViewModelProvider(
            this,
            ViewModelFactory(SettingPreferences.getInstance(dataStore), this)
        )[DashboardViewmodels::class.java]
        viewmodels.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }
        viewmodels.getUser().observe(this) { it ->
            viewmodels.getFutsal(it.id)
            val name = it.name
            val email = it.email
            val id = it.id
            val roles = it.roles
            viewmodels.itemFutsal.observe(this) {
                val model = Auth(
                    name,
                    email,
                    id,
                    roles,
                    true,
                    it.id,
                )
                setupInsert(it)
                setupTransaksi(it)
                binding.tvDashboardFutsal.text = it.name
                viewmodels.saveUser(model)
                Log.e("Futsal123", it.toString())
            }
        }
    }
}