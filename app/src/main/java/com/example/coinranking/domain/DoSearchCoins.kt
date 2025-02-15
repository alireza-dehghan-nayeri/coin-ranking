package com.example.coinranking.domain

import com.example.coinranking.data.repo.CoinsRepository
import javax.inject.Inject

class DoSearchCoins @Inject constructor(
    private val repo: CoinsRepository
) {
    operator fun invoke(searchQuery: String?) = repo.search(searchQuery)
}