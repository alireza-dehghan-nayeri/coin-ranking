package com.example.coinranking.domain

import com.example.coinranking.data.repo.CoinsRepository
import javax.inject.Inject

class GetCoinDetail @Inject constructor(
    private val repo: CoinsRepository
) {
    operator fun invoke(uuid: String) = repo.getCoinDetail(uuid)
}