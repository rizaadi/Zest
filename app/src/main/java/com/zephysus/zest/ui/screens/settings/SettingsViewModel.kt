package com.zephysus.zest.ui.screens.settings

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.viewModelScope
import com.zephysus.core.di.LocalRepository
import com.zephysus.core.model.DarkThemeConfig
import com.zephysus.core.model.NotificationInterval
import com.zephysus.core.repository.QuoteRepository
import com.zephysus.core.repository.UserDataRepository
import com.zephysus.zest.notification.NotificationPermissionHelper
import com.zephysus.zest.notification.QuoteNotificationManager
import com.zephysus.zest.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    @LocalRepository private val quoteRepository: QuoteRepository,
    private val notificationManager: QuoteNotificationManager,
    private val permissionHelper: NotificationPermissionHelper
) : BaseViewModel<SettingsState>(
    initialState = SettingsState()
) {

    init {
        observeUserData()
        checkFeaturedQuotes()
    }

    private fun observeUserData() {
        userDataRepository.userData
            .onEach { userData ->
                setState {
                    it.copy(
                        darkThemeConfig = userData.darkThemeConfig,
                        notificationSettings = userData.notificationSettings
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun checkFeaturedQuotes() {
        quoteRepository.getAllFeaturedQuotes()
            .onEach { result ->
                result.onSuccess { quotes ->
                    setState {
                        it.copy(hasFeaturedQuotes = quotes.isNotEmpty())
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun updateNotificationPermission(hasPermission: Boolean) {
        setState {
            it.copy(hasNotificationPermission = hasPermission)
        }
    }

    fun toggleNotifications() {
        val currentState = state.value

        // Check if we have featured quotes first
        if (!currentState.hasFeaturedQuotes) {
            setState { it.copy(showNoQuotesWarning = true) }
            return
        }

        // Check permission before enabling
        if (!currentState.notificationsEnabled && !currentState.hasNotificationPermission) {
            setState { it.copy(showPermissionDialog = true) }
            return
        }

        viewModelScope.launch {
            if (currentState.notificationsEnabled) {
                notificationManager.disableNotifications()
            } else {
                notificationManager.enableNotifications(currentState.notificationSettings.interval)
            }
        }
    }

    fun updateNotificationInterval(interval: NotificationInterval) {
        viewModelScope.launch {
            val currentSettings = state.value.notificationSettings
            val newSettings = currentSettings.copy(interval = interval)

            userDataRepository.updateNotificationSettings(newSettings)

            // Re-enable with new interval if notifications are currently enabled
            if (newSettings.isEnabled) {
                notificationManager.enableNotifications(interval)
            }
        }
    }

    fun dismissPermissionDialog() {
        setState { it.copy(showPermissionDialog = false) }
    }

    fun dismissNoQuotesWarning() {
        setState { it.copy(showNoQuotesWarning = false) }
    }

    fun onPermissionGranted() {
        setState { it.copy(hasNotificationPermission = true, showPermissionDialog = false) }
        // Try to enable notifications again
        toggleNotifications()
    }

    fun onPermissionDenied() {
        setState { it.copy(hasNotificationPermission = false, showPermissionDialog = false) }
    }

    fun checkNotificationPermission(context: Context) {
        val hasPermission = permissionHelper.hasNotificationPermission(context)
        updateNotificationPermission(hasPermission)
    }

    fun requestNotificationPermission(launcher: ManagedActivityResultLauncher<String, Boolean>) {
        permissionHelper.requestNotificationPermission(launcher)
    }

    fun updateAppearanceMode(mode: DarkThemeConfig) {
        viewModelScope.launch {
            userDataRepository.setDarkThemeConfig(mode)
        }
    }
}