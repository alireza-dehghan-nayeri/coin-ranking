package com.example.coinranking.domain

import com.example.coinranking.data.repo.BookmarksRepository
import javax.inject.Inject

class GetBookmarks @Inject constructor(
    private val repo: BookmarksRepository
) {
    operator fun invoke() = repo.getBookmarks()
}