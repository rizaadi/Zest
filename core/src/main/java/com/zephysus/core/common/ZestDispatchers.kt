package com.zephysus.core.common

import jakarta.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val zestDispatcher: ZestDispatchers)

enum class ZestDispatchers {
    Default, IO
}