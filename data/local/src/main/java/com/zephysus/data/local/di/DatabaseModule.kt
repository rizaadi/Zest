package com.zephysus.data.local.di

import android.content.Context
import androidx.room.Room
import com.zephysus.data.local.ZestDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesZestDatabase(
        @ApplicationContext context: Context,
    ): ZestDatabase = Room.databaseBuilder(
        context,
        ZestDatabase::class.java,
        "zest-database",
    ).build()
}