package com.zephysus.core.repository

import com.zephysus.core.model.DarkThemeConfig
import com.zephysus.core.model.NotificationInterval
import com.zephysus.core.model.NotificationSettings
import com.zephysus.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    /**
     * Stream of [com.zephysus.core.model.UserData]
     */
    val userData: Flow<UserData>

    /**
     * Sets the desired dark theme config.
     */
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    /**
     * Sets whether the user has completed the onboarding process.
     */
    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean)

    /**
     * Updates notification settings.
     */
    suspend fun updateNotificationSettings(settings: NotificationSettings)

    /**
     * Enables notifications with the specified interval.
     */
    suspend fun enableNotifications(interval: NotificationInterval)

    /**
     * Disables notifications.
     */
    suspend fun disableNotifications()

    /**
     * Updates the last notification timestamp.
     */
    suspend fun updateLastNotificationTime(timestamp: Long)
}