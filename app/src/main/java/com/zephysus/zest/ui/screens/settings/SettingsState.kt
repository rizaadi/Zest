package com.zephysus.zest.ui.screens.settings

import com.zephysus.zest.ui.State

data class SettingsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val darkModeEnabled: Boolean = true,
    val notificationsEnabled: Boolean = true,
    val appVersion: String = "1.0.0"
) : State