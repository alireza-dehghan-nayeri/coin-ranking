package com.example.coinranking.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coinranking.data.local.BookmarksDao
import com.example.coinranking.data.local.CoinsDao
import com.example.coinranking.data.local.ExchangesDao
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.Coin
import com.example.coinranking.data.model.Exchange

@Database(
    entities = [
        Coin::class,
        Exchange::class,
        Bookmark::class
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coinsDao(): CoinsDao
    abstract fun exchangesDao(): ExchangesDao
    abstract fun bookmarksDao(): BookmarksDao

}