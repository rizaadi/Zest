package com.zephysus.data.local.di

import com.zephysus.data.local.ZestDatabase
import com.zephysus.data.local.dao.QuotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesQuotesDao(
        database: ZestDatabase,
    ): QuotesDao = database.quotesDao()
}