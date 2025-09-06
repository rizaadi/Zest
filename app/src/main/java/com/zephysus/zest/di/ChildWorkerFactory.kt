package com.zephysus.zest.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

/**
 * Interface for creating WorkManager workers with dependency injection.
 * Used by Hilt to bind AssistedFactory implementations for WorkManager integration.
 */
interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): ListenableWorker
}