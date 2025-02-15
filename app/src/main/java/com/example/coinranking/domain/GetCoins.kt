package com.example.coinranking.domain

import com.example.coinranking.data.repo.CoinsRepository
import javax.inject.Inject

class GetCoins @Inject constructor(
    private val repo: CoinsRepository
) {
    operator fun invoke(limit: Int, timePeriod: String, orderBy: String, orderDirection: String) =
        repo.getCoins(limit, timePeriod, orderBy, orderDirection)
}