package com.example.coinranking.data.repo

import androidx.lifecycle.LiveData
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.data.model.Exchange

interface ExchangesRepository {
    fun getExchanges(): LiveData<Resource<List<Exchange>>>
    fun search(searchQuery: String?): LiveData<Resource<List<Exchange>>>
    fun getExchangeCoins(): LiveData<Resource<List<CoinAndBookmark>>>
    fun getExchangeDetails(uuid: String): LiveData<Resource<Exchange>>
}