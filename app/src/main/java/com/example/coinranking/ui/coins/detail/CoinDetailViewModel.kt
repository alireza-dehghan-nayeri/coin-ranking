package com.example.coinranking.ui.coins.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.coinranking.core.BaseViewModel
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.domain.AddBookmark
import com.example.coinranking.domain.GetCoinDetail
import com.example.coinranking.domain.GetCoinExchanges
import com.example.coinranking.domain.RemoveBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    val addBookmark: AddBookmark,
    val removeBookmark: RemoveBookmark,
    val getCoinDetail: GetCoinDetail,
    val getCoinExchanges: GetCoinExchanges
) : BaseViewModel() {

    private var uuid = ""

    val coinResource: LiveData<Resource<CoinAndBookmark>> = refreshing.switchMap {
        getCoinDetail(uuid)
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

    fun getDetail(uuid: String) {
        this.uuid = uuid
        refresh()
    }
}