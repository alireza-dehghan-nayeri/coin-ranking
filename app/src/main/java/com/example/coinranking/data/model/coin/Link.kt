package com.example.coinranking.data.model.coin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Link(
    val name: String,
    val type: String,
    val url: String
): Parcelable