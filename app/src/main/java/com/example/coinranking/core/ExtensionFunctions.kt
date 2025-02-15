package com.example.coinranking.core

import com.example.coinranking.data.model.Coin
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.data.model.coin.CoinRemote
import com.example.coinranking.data.model.coin.GetCoinResponse
import com.example.coinranking.data.model.exchange.ExchangeRemote
import com.example.coinranking.data.model.exchange.GetExchangeResponse

fun List<CoinRemote>.convertToCoins(): List<Coin> {
    return this.map {
        Coin(
            volume24h = it.volume24h ?: "0",
            btcPrice = it.btcPrice ?: "0",
            change = it.change ?: "0",
            coinrankingUrl = it.coinrankingUrl,
            color = it.color,
            description = it.description ?: "Nothing to show",
            iconUrl = it.iconUrl,
            lowVolume = it.lowVolume,
            marketCap = it.marketCap,
            name = it.name,
            numberOfExchanges = it.numberOfExchanges,
            numberOfMarkets = it.numberOfMarkets,
            price = it.price ?: 0.0,
            rank = it.rank,
            symbol = it.symbol,
            tier = it.tier,
            uuid = it.uuid,
            websiteUrl = it.websiteUrl,
            listedAt = it.listedAt
        )
    }
}

fun List<ExchangeRemote>.convertToExchanges(): List<Exchange> {
    return this.map {
        Exchange(
            volume24h = it.volume24h ?: "0",
            coinrankingUrl = it.coinrankingUrl,
            description = it.description,
            iconUrl = it.iconUrl,
            lastTickerCreatedAt = it.lastTickerCreatedAt,
            marketShare = it.marketShare,
            name = it.name,
            numberOfCoins = it.numberOfCoins,
            numberOfMarkets = it.numberOfMarkets,
            rank = it.rank,
            recommended = it.recommended,
            uuid = it.uuid,
            verified = it.verified,
            websiteUrl = it.websiteUrl
        )
    }
}

fun GetCoinResponse.convertToCoin(): Coin {
    return Coin(
        volume24h = this.data.coin.volume24h ?: "0",
        btcPrice = this.data.coin.btcPrice ?: "0",
        change = this.data.coin.change ?: "0",
        coinrankingUrl = this.data.coin.coinrankingUrl,
        color = this.data.coin.color,
        description = this.data.coin.description ?: "Nothing to show",
        iconUrl = this.data.coin.iconUrl,
        lowVolume = this.data.coin.lowVolume,
        marketCap = this.data.coin.marketCap,
        name = this.data.coin.name,
        numberOfExchanges = this.data.coin.numberOfExchanges,
        numberOfMarkets = this.data.coin.numberOfMarkets,
        price = this.data.coin.price ?: 0.0,
        rank = this.data.coin.rank,
        symbol = this.data.coin.symbol,
        tier = this.data.coin.tier,
        uuid = this.data.coin.uuid,
        websiteUrl = this.data.coin.websiteUrl,
        listedAt = this.data.coin.listedAt
    )
}

fun GetExchangeResponse.convertToExchange(): Exchange {
    return Exchange(
        volume24h = this.data.exchange.volume24h ?: "0",
        coinrankingUrl = this.data.exchange.coinrankingUrl,
        description = this.data.exchange.description,
        iconUrl = this.data.exchange.iconUrl,
        lastTickerCreatedAt = this.data.exchange.lastTickerCreatedAt,
        marketShare = this.data.exchange.marketShare,
        name = this.data.exchange.name,
        numberOfCoins = this.data.exchange.numberOfCoins,
        numberOfMarkets = this.data.exchange.numberOfMarkets,
        rank = this.data.exchange.rank,
        recommended = this.data.exchange.recommended,
        uuid = this.data.exchange.uuid,
        verified = this.data.exchange.verified,
        websiteUrl = this.data.exchange.websiteUrl
    )
}