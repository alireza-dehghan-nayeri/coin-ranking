package com.example.coinranking.domain

import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.repo.BookmarksRepository
import javax.inject.Inject

class RemoveBookmark @Inject constructor(
    private val repo: BookmarksRepository
) {
    operator fun invoke(bookmark: Bookmark) = repo.removeBookmark(bookmark)
}