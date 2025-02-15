package com.example.coinranking.ui.exchanges.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.example.coinranking.core.BaseViewModel
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.domain.GetExchanges
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExchangesViewModel @Inject constructor(
    getExchanges: GetExchanges
) : BaseViewModel() {
    val exchangesResource: LiveData<Resource<List<Exchange>>> = refreshing.switchMap {
        getExchanges()
    }
}