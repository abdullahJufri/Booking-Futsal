package com.bangkit.booking_futsal.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.booking_futsal.data.remote.model.FutsalsItem

class DiffCallback(
    private val mOldFavList: List<FutsalsItem>,
    private val mNewFavList: List<FutsalsItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = mOldFavList.size

    override fun getNewListSize() = mNewFavList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        mOldFavList[oldItemPosition].id == mNewFavList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavList[oldItemPosition]
        val newEmployee = mNewFavList[newItemPosition]
        return oldEmployee.id == newEmployee.id
    }
}