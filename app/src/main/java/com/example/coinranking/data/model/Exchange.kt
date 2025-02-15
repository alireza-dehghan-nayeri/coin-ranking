package com.example.coinranking.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "tbl_exchanges",
    indices = [Index(value = ["uuid"], unique = true)]
)
data class Exchange(
    @PrimaryKey val uuid: String,
    val name: String,
    val volume24h: String,
    val coinrankingUrl: String,
    val description: String?,
    val iconUrl: String,
    val lastTickerCreatedAt: Long?,
    val marketShare: String,
    val numberOfCoins: Int,
    val numberOfMarkets: Int,
    val rank: Int,
    val recommended: Boolean,
    val verified: Boolean,
    val websiteUrl: String?
) : Parcelable
