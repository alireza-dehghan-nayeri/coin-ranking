package com.example.coinranking.data.model.exchange

import com.google.gson.annotations.SerializedName

data class Stats(
    @field:SerializedName("24hVolume")
    val volume24h: String,
    val total: Int
)