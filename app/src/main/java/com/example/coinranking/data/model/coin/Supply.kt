package com.example.coinranking.data.model.coin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Supply(
    val circulating: String,
    val confirmed: Boolean,
    val total: String
) : Parcelable