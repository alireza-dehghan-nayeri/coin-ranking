package com.example.coinranking.data.model.exchange

data class Data(
    val exchanges: List<ExchangeRemote>,
    val stats: Stats?
)