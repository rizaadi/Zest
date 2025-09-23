package com.zephysus.core.model

data class NotificationSettings(
    val isEnabled: Boolean = false,
    val interval: NotificationInterval = NotificationInterval.ONE_HOUR,
    val lastNotificationTime: Long = 0L
)