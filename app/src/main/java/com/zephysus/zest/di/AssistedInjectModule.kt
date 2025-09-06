package com.zephysus.zest.di

import com.zephysus.zest.notification.FeaturedQuoteNotificationWorker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
@InstallIn(SingletonComponent::class)
abstract class AssistedInjectModule {

    @Binds
    @IntoMap
    @StringKey("com.zephysus.zest.notification.FeaturedQuoteNotificationWorker")
    abstract fun bindFeaturedQuoteNotificationWorkerFactory(
        factory: FeaturedQuoteNotificationWorker.Factory
    ): ChildWorkerFactory
}