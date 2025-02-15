package com.example.coinranking.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.coinranking.core.BaseViewModel
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.domain.AddBookmark
import com.example.coinranking.domain.GetBookmarks
import com.example.coinranking.domain.RemoveBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    getBookmarks: GetBookmarks,
    val addBookmark: AddBookmark,
    val removeBookmark: RemoveBookmark
) : BaseViewModel() {
    val bookmarks: LiveData<List<CoinAndBookmark>> = getBookmarks()

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