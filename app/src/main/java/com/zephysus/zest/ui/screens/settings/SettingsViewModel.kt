package com.zephysus.zest.ui.screens.settings

import com.zephysus.zest.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel<SettingsState>(
    initialState = SettingsState()
) {



    fun toggleNotifications() {
        setState { currentState ->
            currentState.copy(notificationsEnabled = !currentState.notificationsEnabled)
        }
    }

    fun clearError() {
        setState { currentState ->
            currentState.copy(error = null)
        }
    }
}