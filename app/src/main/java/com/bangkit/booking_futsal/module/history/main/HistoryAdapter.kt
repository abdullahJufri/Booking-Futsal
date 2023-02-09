package com.bangkit.booking_futsal.module.history.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.booking_futsal.data.remote.model.HistoryItem
import com.bangkit.booking_futsal.databinding.ItemRowHistoryBinding
import com.bangkit.booking_futsal.module.history.detail.HistoryDetailActivity
import com.bangkit.booking_futsal.utils.DiffCallback2

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val listHistory = ArrayList<HistoryItem>()

    fun setListHistory(itemHistory: List<HistoryItem>) {
        val diffCallback = DiffCallback2(this.listHistory, itemHistory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listHistory.clear()
        this.listHistory.addAll(itemHistory)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listHistory[position])

    }

    override fun getItemCount() = listHistory.size

    class ViewHolder(private var binding: ItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryItem) {

            with(binding) {

                tvItemName.text = history.name
                tvItemTanggal.text = history.tanggal
                tvHistoryHarga.text = history.harga
                tvHistoryJam.text = history.jam
                tvOrder.text = history.orderId
                tvItemLap.text = history.nama_lapangan
                itemView.setOnClickListener {
//
                    val intent = Intent(itemView.context, HistoryDetailActivity::class.java)
                    intent.putExtra(HistoryDetailActivity.EXTRA_HISTORY, history)
                    itemView.context.startActivity(intent,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity)
                            .toBundle()
                    )
                }


            }
        }

    }
}