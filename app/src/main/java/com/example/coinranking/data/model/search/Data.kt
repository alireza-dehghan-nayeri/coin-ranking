package com.example.coinranking.data.model.search

data class Data(
    val coins: List<CoinSearchRemote>,
    val exchanges: List<ExchangeSearchRemote>
)