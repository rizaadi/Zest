package com.zephysus.zest.notification

import android.content.Context
import com.zephysus.core.model.NotificationInterval
import com.zephysus.core.repository.UserDataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userDataRepository: UserDataRepository,
    private val notificationScheduler: NotificationScheduler,
    private val permissionHelper: NotificationPermissionHelper
) {

    suspend fun enableNotifications(interval: NotificationInterval): Boolean {
        return if (permissionHelper.hasNotificationPermission(context)) {
            userDataRepository.enableNotifications(interval)
            notificationScheduler.scheduleNotifications(interval)
            true
        } else {
            false
        }
    }

    suspend fun disableNotifications() {
        userDataRepository.disableNotifications()
        notificationScheduler.cancelNotifications()
    }

    suspend fun isEnabled(): Boolean {
        return userDataRepository.userData.first().notificationSettings.isEnabled
    }

    fun hasPermission(): Boolean = permissionHelper.hasNotificationPermission(context)
}