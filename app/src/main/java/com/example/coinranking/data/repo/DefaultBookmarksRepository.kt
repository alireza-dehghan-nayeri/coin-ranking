package com.example.coinranking.data.repo

import androidx.lifecycle.LiveData
import com.example.coinranking.data.local.BookmarksDao
import com.example.coinranking.data.local.ExchangesDao
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark
import javax.inject.Inject

class DefaultBookmarksRepository @Inject constructor(
    private val bookmarksDao: BookmarksDao,
    private val exchangesDao: ExchangesDao,
) :
    BookmarksRepository {
    override fun getBookmarks(): LiveData<List<CoinAndBookmark>> {
        return bookmarksDao.getAllBookmarks()
    }

    override fun addBookmark(bookmark: Bookmark) {
        bookmarksDao.insertBookmark(bookmark)
    }

    override fun removeBookmark(bookmark: Bookmark) {
        bookmarksDao.deleteBookmark(bookmark)
    }
}