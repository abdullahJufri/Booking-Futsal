package com.bangkit.booking_futsal.module.admin.transaksi

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.booking_futsal.data.remote.model.TransaksiItem
import com.bangkit.booking_futsal.databinding.ItemRowHistoryBinding
import com.bangkit.booking_futsal.module.history.detail.HistoryDetailActivity
import com.bangkit.booking_futsal.utils.DiffCallback3

class TransaksiAdapter : RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

    private val listTransaksi = ArrayList<TransaksiItem>()

    fun setListHistory(itemTransaksi: List<TransaksiItem>) {
        val diffCallback = DiffCallback3(this.listTransaksi, itemTransaksi)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listTransaksi.clear()
        this.listTransaksi.addAll(itemTransaksi)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTransaksi[position])

    }

    override fun getItemCount() = listTransaksi.size

    class ViewHolder(private var binding: ItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaksi: TransaksiItem) {

            with(binding) {

                tvItemName.text = transaksi.name
                tvItemTanggal.text = transaksi.tanggal
                tvHistoryHarga.text = transaksi.harga
                tvHistoryJam.text = transaksi.jam
                tvOrder.text = transaksi.orderId
                tvItemLap.text = transaksi.namaLapangan

//                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, HistoryDetailActivity::class.java)
//                    intent.putExtra(HistoryDetailActivity.EXTRA_HISTORY, transaksi)
//                    itemView.context.startActivity(
//                        intent,
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity)
//                            .toBundle()
//                    )
//                }


            }
        }

    }
}