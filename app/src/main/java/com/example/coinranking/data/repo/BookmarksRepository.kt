package com.example.coinranking.data.repo

import androidx.lifecycle.LiveData
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark

interface BookmarksRepository {
    fun getBookmarks(): LiveData<List<CoinAndBookmark>>
    fun addBookmark(bookmark: Bookmark)
    fun removeBookmark(bookmark: Bookmark)
}