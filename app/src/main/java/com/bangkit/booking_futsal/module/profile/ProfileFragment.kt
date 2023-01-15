package com.bangkit.booking_futsal.module.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.databinding.FragmentProfileBinding
import com.bangkit.booking_futsal.module.auth.AuthActivity
import com.bangkit.booking_futsal.module.main.MainViewmodels
import com.bangkit.booking_futsal.utils.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewmodel: MainViewmodels


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        viewmodel.getUser().observe(viewLifecycleOwner) {
            binding.tvPrfName.text = it.name
            binding.tvPrfEmail.text = it.email
        }
        binding.btnLogout.setOnClickListener {
            viewmodel.logout()
            val intent = Intent(activity, AuthActivity::class.java)
            startActivity(intent)



        }
    }

    private fun setupViewModel() {
        viewmodel = ViewModelProvider(
            this,
            ViewModelFactory(
                SettingPreferences.getInstance(requireContext().dataStore),
                requireActivity().application
            )
        )[MainViewmodels::class.java]
    }


}