package com.example.coinranking.ui.coins.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.example.coinranking.core.BaseViewModel
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.domain.GetCoinExchanges
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinDetailExchangesViewModel @Inject constructor(
    getCoinExchanges: GetCoinExchanges
) : BaseViewModel() {

    val coinExchangesResource: LiveData<Resource<List<Exchange>>> = refreshing.switchMap {
        getCoinExchanges()
    }

}