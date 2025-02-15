package com.example.coinranking.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark

@Dao
interface BookmarksDao {

    @Delete
    fun deleteBookmark(bookmark: Bookmark)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM tbl_coins INNER JOIN tbl_bookmarks ON tbl_coins.uuid=tbl_bookmarks.uuid")
    fun getAllBookmarks(): LiveData<List<CoinAndBookmark>>
}