package com.zephysus.zest.ui.screens.settings

import com.zephysus.core.model.NotificationSettings
import com.zephysus.zest.ui.State

data class SettingsState(
    val darkModeEnabled: Boolean = true,
    val notificationSettings: NotificationSettings = NotificationSettings(),
    val hasNotificationPermission: Boolean = false,
    val hasFeaturedQuotes: Boolean = false,
    val showPermissionDialog: Boolean = false,
    val showNoQuotesWarning: Boolean = false,
    val appVersion: String = "1.0.0"
) : State {
    val notificationsEnabled: Boolean
        get() = notificationSettings.isEnabled
}