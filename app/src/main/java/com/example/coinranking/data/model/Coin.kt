package com.example.coinranking.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "tbl_coins",
    indices = [Index(value = ["uuid"], unique = true)]
)
data class Coin(
    @PrimaryKey val uuid: String,
    val name: String,
    val symbol: String,
    val iconUrl: String,
    val price: Double,
    val volume24h: String?,
    val description: String?,
    val btcPrice: String,
    val change: String,
    val coinrankingUrl: String,
    val color: String?,
    val lowVolume: Boolean,
    val marketCap: Double?,
    val numberOfExchanges: Int?,
    val numberOfMarkets: Int?,
    val rank: Int,
    val tier: Int,
    val websiteUrl: String?,
    val listedAt: Int?
    //@Ignore val allTimeHigh: AllTimeHigh?,
) : Parcelable
