package com.zephysus.core.di

import com.zephysus.core.repository.LocalQuoteRepositoryImpl
import com.zephysus.core.repository.QuoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @LocalRepository
    fun zestLocalRepository(localRepository: LocalQuoteRepositoryImpl): QuoteRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRepository
