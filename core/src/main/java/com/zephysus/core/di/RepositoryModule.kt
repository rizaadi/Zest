package com.zephysus.core.di

import com.zephysus.core.repository.LocalQuoteRepositoryImpl
import com.zephysus.core.repository.QuoteRepository
import com.zephysus.core.repository.UserDataRepository
import com.zephysus.core.repository.UserDataRepositoryImpl
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
    fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl
    ): UserDataRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRepository
