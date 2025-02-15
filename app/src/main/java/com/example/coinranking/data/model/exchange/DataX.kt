package com.example.coinranking.data.model.exchange

import com.example.coinranking.data.model.coin.CoinRemote

data class DataX(
    val exchange: ExchangeRemote,
    val stats: Stats
)