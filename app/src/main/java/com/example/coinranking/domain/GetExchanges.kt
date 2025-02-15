package com.example.coinranking.domain

import com.example.coinranking.data.repo.ExchangesRepository
import javax.inject.Inject

class GetExchanges @Inject constructor(
    private val repo: ExchangesRepository
) {
    operator fun invoke() = repo.getExchanges()
}