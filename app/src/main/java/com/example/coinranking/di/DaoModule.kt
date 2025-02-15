package com.example.coinranking.di

import com.example.coinranking.core.AppDatabase
import com.example.coinranking.data.local.BookmarksDao
import com.example.coinranking.data.local.CoinsDao
import com.example.coinranking.data.local.ExchangesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
object DaoModule {

    @Provides
    @Singleton
    fun provideCoinsDao(db: AppDatabase): CoinsDao = db.coinsDao()

    @Provides
    @Singleton
    fun provideExchangesDao(db: AppDatabase): ExchangesDao = db.exchangesDao()

    @Provides
    @Singleton
    fun provideBookmarksDao(db: AppDatabase): BookmarksDao = db.bookmarksDao()

}