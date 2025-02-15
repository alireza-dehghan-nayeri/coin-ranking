package com.example.coinranking.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinAndBookmark(
    @Embedded val coin: Coin,
    @Relation(
        parentColumn = "uuid",
        entityColumn = "uuid"
    )
    var bookmark: Bookmark?
) : Parcelable
