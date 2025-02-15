package com.example.coinranking.data.model.coin

import com.google.gson.annotations.SerializedName

data class CoinRemote(
    @field:SerializedName("24hVolume")
    val volume24h: String?,
//    @Ignore val allTimeHigh: AllTimeHigh?,
    val btcPrice: String?,
    val change: String?,
    val coinrankingUrl: String,
    val color: String?,
    val description: String?,
    val iconUrl: String,
    val lowVolume: Boolean,
    val marketCap: Double?,
    val name: String,
    val numberOfExchanges: Int?,
    val numberOfMarkets: Int?,
    val price: Double?,
    val rank: Int,
    val symbol: String,
    val tier: Int,
    val uuid: String,
    val websiteUrl: String?,
    val listedAt: Int?
)
