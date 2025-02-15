package com.example.coinranking.data.repo

import androidx.lifecycle.LiveData
import com.example.coinranking.core.*
import com.example.coinranking.data.local.CoinsDao
import com.example.coinranking.data.local.ExchangesDao
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.data.model.coin.GetCoinsResponse
import com.example.coinranking.data.model.exchange.GetExchangeResponse
import com.example.coinranking.data.model.exchange.GetExchangesResponse
import com.example.coinranking.data.remote.WebService
import retrofit2.Response
import javax.inject.Inject


class DefaultExchangesRepository @Inject constructor(
    private val webService: WebService,
    private val exchangesDao: ExchangesDao,
    private val coinsDao: CoinsDao
) : ExchangesRepository {

    override fun getExchanges(
    ): LiveData<Resource<List<Exchange>>> =
        object : NetworkBoundResource<List<Exchange>, GetExchangesResponse>() {
            override suspend fun saveCallResult(response: GetExchangesResponse) {
                return exchangesDao.insertAllExchanges(response.data.exchanges.convertToExchanges())
            }

            override fun loadFromDb(): LiveData<List<Exchange>> {
                return exchangesDao.getAllExchanges()
            }

            override suspend fun createCall(): Response<GetExchangesResponse> {
                return webService.getExchanges()
            }

        }.asLiveData()

    override fun getExchangeDetails(uuid: String): LiveData<Resource<Exchange>> =
        object : NetworkBoundResource<Exchange, GetExchangeResponse>() {
            override suspend fun saveCallResult(response: GetExchangeResponse) {
                exchangesDao.updateExchange(
                    response.convertToExchange()
                )
            }

            override fun loadFromDb(): LiveData<Exchange> {
                return exchangesDao.getExchange(uuid)
            }

            override suspend fun createCall(): Response<GetExchangeResponse> {
                return webService.getExchange(uuid)
            }

        }.asLiveData()


    override fun search(searchQuery: String?): LiveData<Resource<List<Exchange>>> {

        return object : NetworkBoundResource<List<Exchange>, GetExchangesResponse>() {
            override suspend fun saveCallResult(response: GetExchangesResponse) {
                exchangesDao.insertAllExchanges(response.data.exchanges.convertToExchanges())
            }

            override fun loadFromDb(): LiveData<List<Exchange>> {
                return exchangesDao.getSearchExchanges(searchQuery)
            }

            override suspend fun createCall(): Response<GetExchangesResponse> {
                return webService.getExchangesSearch(searchQuery)
            }

        }.asLiveData()
    }

    override fun getExchangeCoins(): LiveData<Resource<List<CoinAndBookmark>>> {
        return object : NetworkBoundResource<List<CoinAndBookmark>, GetCoinsResponse>() {
            override suspend fun saveCallResult(response: GetCoinsResponse) {
                coinsDao.insertAllCoins(response.data.coins.convertToCoins())
            }

            override fun loadFromDb(): LiveData<List<CoinAndBookmark>> {
                return exchangesDao.getExchangeCoins()
            }

            override suspend fun createCall(): Response<GetCoinsResponse> {
                return webService.getCoinsWithLimit()
            }

        }.asLiveData()
    }
}
