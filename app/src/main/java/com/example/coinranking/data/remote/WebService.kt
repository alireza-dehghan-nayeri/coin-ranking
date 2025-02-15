package com.example.coinranking.data.remote

import com.example.coinranking.data.model.coin.GetCoinResponse
import com.example.coinranking.data.model.coin.GetCoinsResponse
import com.example.coinranking.data.model.exchange.GetExchangeResponse
import com.example.coinranking.data.model.exchange.GetExchangesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {

    @GET("coins")
    suspend fun getCoins(
        @Query("limit") limit: Int = 100,
        @Query("timePeriod") timePeriod: String = "24h",
        @Query("orderBy") orderBy: String = "marketCap",
        @Query("orderDirection") orderDirection: String = "desc"
    ): Response<GetCoinsResponse>

    @GET("coin/{uuid}")
    suspend fun getCoin(@Path("uuid") uuid: String): Response<GetCoinResponse>

    @GET("exchange/{uuid}")
    suspend fun getExchange(@Path("uuid") uuid: String): Response<GetExchangeResponse>

    @GET("coins")
    suspend fun getCoinsWithLimit(@Query("limit") limit: Int = 10): Response<GetCoinsResponse>

    @GET("exchanges")
    suspend fun getExchanges(): Response<GetExchangesResponse>

    @GET("exchanges")
    suspend fun getExchangesWithLimit(@Query("limit") limit: Int = 10): Response<GetExchangesResponse>

    @GET("coins")
    suspend fun getCoinsSearch(@Query("search") searchQuery: String?): Response<GetCoinsResponse>

    @GET("exchanges")
    suspend fun getExchangesSearch(@Query("search") searchQuery: String?): Response<GetExchangesResponse>
}