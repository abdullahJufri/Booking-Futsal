package com.bangkit.booking_futsal.module.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.booking_futsal.databinding.FragmentHomeBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //    private lateinit var viewmodel: HomeViewmodels
    private val viewModel: HomeViewmodels by viewModels()
    private lateinit var adapter: HomeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListFutsal()
        showRecyclerView()

    }

    private fun showRecyclerView() {
        binding.rvFutsals.layoutManager = LinearLayoutManager(activity)
        adapter = HomeAdapter()
        viewModel.itemFutsal.observe(viewLifecycleOwner) {
//            binding.swipeRefreshLayout.isRefreshing = false
            adapter.setListStory(it)
        }
        binding.rvFutsals.adapter = adapter
    }

//    fun setData(itemStory: List<FutsalsItem>) {
//        val diffCallback = DiffCallback(this.itemfutsal, itemStory)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//
//        this.listStory.clear()
//        this.listStory.addAll(itemStory)
//        diffResult.dispatchUpdatesTo(this)
//    }

    private fun setListFutsal() {
        viewModel.showListFutsal()
        viewModel.itemFutsal.observe(viewLifecycleOwner) {
            adapter.setListStory(it)
        }
    }
}