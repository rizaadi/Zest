package com.zephysus.core.di

import com.zephysus.core.repository.LocalQuoteRepositoryImpl
import com.zephysus.core.repository.NotificationPreferences
import com.zephysus.core.repository.NotificationRepository
import com.zephysus.core.repository.QuoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @LocalRepository
    fun zestLocalRepository(localRepository: LocalQuoteRepositoryImpl): QuoteRepository

    @Binds
    @Singleton
    fun bindNotificationRepository(
        notificationPreferences: NotificationPreferences
    ): NotificationRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRepository
