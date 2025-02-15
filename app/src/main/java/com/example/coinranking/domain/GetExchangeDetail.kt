package com.example.coinranking.domain

import com.example.coinranking.data.repo.ExchangesRepository
import javax.inject.Inject

class GetExchangeDetail @Inject constructor(
    private val repo: ExchangesRepository
) {
    operator fun invoke(uuid: String) = repo.getExchangeDetails(uuid)
}