package com.example.coinranking.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tbl_bookmarks")
data class Bookmark(
    @PrimaryKey() val uuid: String

) : Parcelable
