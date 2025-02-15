package com.example.coinranking.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.coinranking.core.BaseViewModel
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val doSearchCoins: DoSearchCoins,
    val doSearchExchanges: DoSearchExchanges,
    val getBookmarks: GetBookmarks,
    val addBookmark: AddBookmark,
    val removeBookmark: RemoveBookmark
) : BaseViewModel() {

    private val searchRefreshing = MutableLiveData<Boolean>()

    private fun searchRefresh() {
        searchRefreshing.value = true
    }

    var coinSearchQuery: String? = ""
    var exchangeSearchQuery: String? = ""

    val coinsSearchResultList: LiveData<Resource<List<CoinAndBookmark>>> =
        searchRefreshing.switchMap {
            doSearchCoins(coinSearchQuery)
        }
    val exchangesSearchResultList: LiveData<Resource<List<Exchange>>> =
        searchRefreshing.switchMap {
            doSearchExchanges(exchangeSearchQuery)
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

    fun search(searchQuery: String?) {
        coinSearchQuery = searchQuery
        exchangeSearchQuery = searchQuery
        searchRefresh()
    }

}