package com.zephysus.zest.notification

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.zephysus.core.model.NotificationInterval
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    // Lazy initialization to ensure WorkManager is configured first
    private val workManager by lazy { WorkManager.getInstance(context) }

    fun scheduleNotifications(interval: NotificationInterval) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED) // Local quotes only
            .build()

        val workRequest = PeriodicWorkRequestBuilder<FeaturedQuoteNotificationWorker>(
            repeatInterval = interval.intervalMinutes,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .addTag(FeaturedQuoteNotificationWorker.WORK_NAME)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            FeaturedQuoteNotificationWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun cancelNotifications() {
        workManager.cancelUniqueWork(FeaturedQuoteNotificationWorker.WORK_NAME)
    }

}