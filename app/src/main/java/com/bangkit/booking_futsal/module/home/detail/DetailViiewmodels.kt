package com.bangkit.booking_futsal.module.home.detail

import androidx.lifecycle.ViewModel
import com.bangkit.booking_futsal.data.remote.model.FutsalsItem

class DetailViiewmodels : ViewModel() {
    lateinit var futsalsItem: FutsalsItem

    fun setDetailStory(futsal: FutsalsItem) : FutsalsItem{
        futsalsItem = futsal
        return futsalsItem
    }
}