package com.example.coinranking.data.repo

import androidx.lifecycle.LiveData
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.data.model.Exchange

interface CoinsRepository {
    fun getCoins(
        limit: Int,
        timePeriod: String,
        orderBy: String,
        orderDirection: String
    ): LiveData<Resource<List<CoinAndBookmark>>>

    fun search(searchQuery: String?): LiveData<Resource<List<CoinAndBookmark>>>
    fun getCoinExchanges(): LiveData<Resource<List<Exchange>>>
    fun getCoinDetail(uuid: String): LiveData<Resource<CoinAndBookmark>>
}