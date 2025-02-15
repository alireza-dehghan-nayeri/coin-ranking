package com.example.coinranking.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.coinranking.data.model.Coin
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.data.model.Exchange

@Dao
interface CoinsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCoins(coins: List<Coin>)

    @Query("select * from tbl_coins order by CASE :orderBy when 'price' then price when 'marketCap' then marketCap end desc")
    fun getAllCoinsDesc(orderBy: String): LiveData<List<CoinAndBookmark>>

    @Query("select * from tbl_coins order by CASE :orderBy when 'price' then price when 'marketCap' then marketCap end asc")
    fun getAllCoinsAsc(orderBy: String): LiveData<List<CoinAndBookmark>>

    @Update
    fun updateCoin(coin: Coin)

    @Query("SELECT * FROM tbl_coins WHERE uuid = :uuid")
    fun getCoin(uuid: String): LiveData<CoinAndBookmark>

    @Query("SELECT * FROM tbl_coins WHERE name LIKE :searchQuery || '%'")
    fun getSearchCoins(searchQuery: String?): LiveData<List<CoinAndBookmark>>

    @Query("SELECT * FROM tbl_exchanges limit 10")
    fun getCoinExchanges(): LiveData<List<Exchange>>
}