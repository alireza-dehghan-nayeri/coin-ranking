package com.example.coinranking.ui.coins.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.coinranking.core.BaseViewModel
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.domain.AddBookmark
import com.example.coinranking.domain.GetBookmarks
import com.example.coinranking.domain.GetCoins
import com.example.coinranking.domain.RemoveBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    val getCoins: GetCoins,
    val getBookmarks: GetBookmarks,
    val addBookmark: AddBookmark,
    val removeBookmark: RemoveBookmark
) : BaseViewModel() {

    private var limit = 100
    private var timePeriod = "24h"
    private var orderBy = "marketCap"
    private var orderDirection = "desc"

    val coinsResource: LiveData<Resource<List<CoinAndBookmark>>> = refreshing.switchMap {
        getCoins(limit, timePeriod, orderBy, orderDirection)
    }

    fun addCoinToBookmarks(bookmark: Bookmark) {
        viewModelScope.launch(Dispatchers.IO) {
            addBookmark(bookmark)
        }
    }

    fun removeCoinFromBookmarks(bookmark: Bookmark) {
        viewModelScope.launch(Dispatchers.IO) {
            removeBookmark(bookmark)
        }
    }

    fun setSortArguments(
        limit: Int = this.limit,
        timePeriod: String = this.timePeriod,
        orderBy: String = this.orderBy,
        orderDirection: String = this.orderDirection
    ) {
        this.limit = limit
        this.timePeriod = timePeriod
        this.orderBy = orderBy
        this.orderDirection = orderDirection
        refresh()
    }
}