package com.example.coinranking.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.data.model.Exchange

@Dao
interface ExchangesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllExchanges(exchanges: List<Exchange>)

    @Query("SELECT * FROM tbl_exchanges")
    fun getAllExchanges(): LiveData<List<Exchange>>

    @Update
    fun updateExchange(exchange: Exchange)

    @Query("SELECT * FROM tbl_exchanges WHERE uuid = :uuid")
    fun getExchange(uuid: String): LiveData<Exchange>

    @Query("SELECT * FROM tbl_exchanges WHERE name LIKE :searchQuery || '%'")
    fun getSearchExchanges(searchQuery: String?): LiveData<List<Exchange>>

    @Query("SELECT * FROM tbl_coins limit 10")
    fun getExchangeCoins(): LiveData<List<CoinAndBookmark>>
}