package com.example.coinranking.data.model.exchange

import com.google.gson.annotations.SerializedName


data class ExchangeRemote(
    @field:SerializedName("24hVolume")
    val volume24h: String?,
    val coinrankingUrl: String,
    val description: String?,
    val iconUrl: String,
    val lastTickerCreatedAt: Long?,
//    val links: List<Link>?,
    val marketShare: String,
    val name: String,
    val numberOfCoins: Int,
    val numberOfMarkets: Int,
    val rank: Int,
    val recommended: Boolean,
    val uuid: String,
    val verified: Boolean,
    val websiteUrl: String?
)
