package com.example.coinranking.di

import android.content.Context
import androidx.room.Room
import com.example.coinranking.core.AppDatabase
import com.example.coinranking.data.repo.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "my_retrofit_sample_db").build()
    }

    @Singleton
    @Binds
    abstract fun bindCoinsRepository(input: DefaultCoinsRepository): CoinsRepository

    @Singleton
    @Binds
    abstract fun bindExchangesRepository(input: DefaultExchangesRepository): ExchangesRepository

    @Singleton
    @Binds
    abstract fun bindBookmarksRepository(input: DefaultBookmarksRepository): BookmarksRepository

}