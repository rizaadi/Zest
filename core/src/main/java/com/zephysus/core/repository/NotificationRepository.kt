package com.zephysus.core.repository

import com.zephysus.core.model.NotificationInterval
import com.zephysus.core.model.NotificationSettings
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotificationSettings(): Flow<NotificationSettings>
    suspend fun updateNotificationSettings(settings: NotificationSettings)
    suspend fun enableNotifications(interval: NotificationInterval)
    suspend fun disableNotifications()
    suspend fun updateLastNotificationTime(timestamp: Long)
}