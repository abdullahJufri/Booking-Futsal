package com.bangkit.booking_futsal.module.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.booking_futsal.data.local.SettingPreferences
import com.bangkit.booking_futsal.databinding.FragmentHistoryBinding
import com.bangkit.booking_futsal.utils.ViewModelFactory
import com.bangkit.booking_futsal.utils.showLoading


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    //
//    private val viewModel: HistoryViewmodels by viewModels()
    private lateinit var viewmodel: HistoryViewmodels
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        viewmodel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it, binding.progressBar)
        }
        setListHistory()
        showRecyclerView()

    }

    private fun setupViewModel() {
        viewmodel = ViewModelProvider(
            this,
            ViewModelFactory(
                SettingPreferences.getInstance(requireContext().dataStore),
                requireActivity().application
            )
        )[HistoryViewmodels::class.java]
    }

    private fun showRecyclerView() {
        binding.rvHistory.layoutManager = LinearLayoutManager(activity)
        adapter = HistoryAdapter()
        viewmodel.itemHistory.observe(viewLifecycleOwner) {
//            binding.swipeRefreshLayout.isRefreshing = false
            adapter.setListHistory(it)
        }
        binding.rvHistory.adapter = adapter
    }

    private fun setListHistory() {
        viewmodel.getUser().observe(viewLifecycleOwner) {
            viewmodel.showListHistory(it.id)
        }
        viewmodel.itemHistory.observe(viewLifecycleOwner) {
            adapter.setListHistory(it)
        }
    }

}