package com.example.coinranking.ui.exchanges.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.coinranking.core.BaseViewModel
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.domain.AddBookmark
import com.example.coinranking.domain.GetBookmarks
import com.example.coinranking.domain.GetExchangeCoins
import com.example.coinranking.domain.RemoveBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeDetailCoinsViewModel @Inject constructor(
    getExchangeCoins: GetExchangeCoins,
    getBookmarks: GetBookmarks,
    val addBookmark: AddBookmark,
    val removeBookmark: RemoveBookmark,
) : BaseViewModel() {
    val exchangeCoinsResource: LiveData<Resource<List<CoinAndBookmark>>> = refreshing.switchMap {
        getExchangeCoins()
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

}