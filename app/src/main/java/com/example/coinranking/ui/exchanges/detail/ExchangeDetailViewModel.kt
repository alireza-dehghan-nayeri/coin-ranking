package com.example.coinranking.ui.exchanges.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.example.coinranking.core.BaseViewModel
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.domain.GetExchangeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExchangeDetailViewModel @Inject constructor(
    val getExchangeDetail: GetExchangeDetail
) : BaseViewModel() {

    private var uuid = ""

    val exchangeResource: LiveData<Resource<Exchange>> = refreshing.switchMap {
        getExchangeDetail(uuid)
    }

    fun getDetail(uuid: String) {
        this.uuid = uuid
        refresh()
    }

}