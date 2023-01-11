package com.bangkit.booking_futsal.module.home.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.booking_futsal.R
import com.bangkit.booking_futsal.data.remote.model.FutsalsItem
import com.bangkit.booking_futsal.databinding.ItemRowFutsalBinding
import com.bangkit.booking_futsal.module.home.detail.DetailActivity
import com.bangkit.booking_futsal.utils.DiffCallback
import com.bumptech.glide.Glide

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val listFutsal = ArrayList<FutsalsItem>()

    fun setListStory(itemFutsal: List<FutsalsItem>) {
        val diffCallback = DiffCallback(this.listFutsal, itemFutsal)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listFutsal.clear()
        this.listFutsal.addAll(itemFutsal)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowFutsalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFutsal[position])

    }

    override fun getItemCount() = listFutsal.size

    class ViewHolder(private var binding: ItemRowFutsalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgImage: ImageView = binding.imgItemImage
        val tvName: TextView = binding.tvItemName
        val tvHarga: TextView = binding.tvItemHarga
        val tvAlamat: TextView = binding.tvItemAlamat
        fun bind(futsal: FutsalsItem) {

            with(binding) {
                Glide.with(imgItemImage)
                    .load(futsal.foto) // URL Avatar
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(imgItemImage)
                tvItemName.text = futsal.name
                tvItemHarga.text = futsal.hargaPagi
                tvItemAlamat.text = futsal.alamatLapangan
                itemView.setOnClickListener {
//                    val optionsCompat: ActivityOptionsCompat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            itemView.context as Activity,
//                            Pair(imgImage, "photos"),
//                            Pair(tvName, "name"),
//                            Pair(tvHarga, "harga"),
//                            Pair(tvAlamat, "alamat"),
//                        )
//                    val optionsCompat: ActivityOptionsCompat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            itemView.context as Activity,
//                            Pair(imgImage, "photos"),
//                            Pair(tvName, "name"),
//                            Pair(tvHarga, "harga"),
//                            Pair(tvAlamat, "alamat"),
//                        )
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_FUTSAL, futsal)
//                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                    itemView.context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity).toBundle())
                }


            }
        }

    }
}