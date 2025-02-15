package com.example.coinranking.data.repo

import androidx.lifecycle.LiveData
import com.example.coinranking.core.*
import com.example.coinranking.data.local.CoinsDao
import com.example.coinranking.data.local.ExchangesDao
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.data.model.coin.GetCoinResponse
import com.example.coinranking.data.model.coin.GetCoinsResponse
import com.example.coinranking.data.model.exchange.GetExchangesResponse
import com.example.coinranking.data.remote.WebService
import retrofit2.Response
import javax.inject.Inject

class DefaultCoinsRepository @Inject constructor(
    private val webService: WebService,
    private val coinsDao: CoinsDao,
    private val exchangesDao: ExchangesDao
) : CoinsRepository {

    override fun getCoins(
        limit: Int,
        timePeriod: String,
        orderBy: String,
        orderDirection: String
    ): LiveData<Resource<List<CoinAndBookmark>>> =
        object : NetworkBoundResource<List<CoinAndBookmark>, GetCoinsResponse>() {
            override suspend fun saveCallResult(response: GetCoinsResponse) {
                coinsDao.insertAllCoins(response.data.coins.convertToCoins())
            }

            override fun loadFromDb(): LiveData<List<CoinAndBookmark>> {
                return if (orderDirection == "desc") {
                    coinsDao.getAllCoinsDesc(orderBy)
                } else {
                    coinsDao.getAllCoinsAsc(orderBy)
                }
            }

            override suspend fun createCall(): Response<GetCoinsResponse> {
                return webService.getCoins(limit, timePeriod, orderBy, orderDirection)
            }

        }.asLiveData()

    override fun getCoinDetail(uuid: String): LiveData<Resource<CoinAndBookmark>> =
        object : NetworkBoundResource<CoinAndBookmark, GetCoinResponse>() {
            override suspend fun saveCallResult(response: GetCoinResponse) {
                coinsDao.updateCoin(
                    response.convertToCoin()
                )
            }

            override fun loadFromDb(): LiveData<CoinAndBookmark> {
                return coinsDao.getCoin(uuid)
            }

            override suspend fun createCall(): Response<GetCoinResponse> {
                return webService.getCoin(uuid)
            }

        }.asLiveData()

    override fun getCoinExchanges(): LiveData<Resource<List<Exchange>>> {
        return object : NetworkBoundResource<List<Exchange>, GetExchangesResponse>() {
            override suspend fun saveCallResult(response: GetExchangesResponse) {
                exchangesDao.insertAllExchanges(response.data.exchanges.convertToExchanges())
            }

            override fun loadFromDb(): LiveData<List<Exchange>> {
                return coinsDao.getCoinExchanges()
            }

            override suspend fun createCall(): Response<GetExchangesResponse> {
                return webService.getExchangesWithLimit()
            }

        }.asLiveData()
    }

    override fun search(searchQuery: String?): LiveData<Resource<List<CoinAndBookmark>>> {

        return object : NetworkBoundResource<List<CoinAndBookmark>, GetCoinsResponse>() {
            override suspend fun saveCallResult(response: GetCoinsResponse) {
                coinsDao.insertAllCoins(response.data.coins.convertToCoins())
            }

            override fun loadFromDb(): LiveData<List<CoinAndBookmark>> {
                return coinsDao.getSearchCoins(searchQuery)
            }

            override suspend fun createCall(): Response<GetCoinsResponse> {
                return webService.getCoinsSearch(searchQuery)
            }

        }.asLiveData()
    }
}