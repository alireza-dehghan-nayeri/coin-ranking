package com.example.coinranking.data.model.coin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllTimeHigh(
    val price: String,
    val timestamp: Int
): Parcelable