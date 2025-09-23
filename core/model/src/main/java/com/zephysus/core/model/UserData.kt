package com.zephysus.core.model

data class UserData(
    val darkThemeConfig: DarkThemeConfig,
    val shouldHideOnboarding: Boolean,
    val notificationSettings: NotificationSettings
)
