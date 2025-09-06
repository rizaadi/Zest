package com.zephysus.core.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zephysus.core.model.NotificationInterval
import com.zephysus.core.model.NotificationSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "notification_settings")

@Singleton
class NotificationPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) : NotificationRepository {

    companion object {
        private val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        private val NOTIFICATION_INTERVAL = longPreferencesKey("notification_interval")
        private val LAST_NOTIFICATION_TIME = longPreferencesKey("last_notification_time")
    }

    override fun getNotificationSettings(): Flow<NotificationSettings> {
        return context.dataStore.data.map { preferences ->
            NotificationSettings(
                isEnabled = preferences[NOTIFICATIONS_ENABLED] ?: false,
                interval = NotificationInterval.fromMinutes(
                    preferences[NOTIFICATION_INTERVAL]
                        ?: NotificationInterval.ONE_HOUR.intervalMinutes
                ) ?: NotificationInterval.ONE_HOUR,
                lastNotificationTime = preferences[LAST_NOTIFICATION_TIME] ?: 0L
            )
        }
    }

    override suspend fun updateNotificationSettings(settings: NotificationSettings) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED] = settings.isEnabled
            preferences[NOTIFICATION_INTERVAL] = settings.interval.intervalMinutes
            preferences[LAST_NOTIFICATION_TIME] = settings.lastNotificationTime
        }
    }

    override suspend fun enableNotifications(interval: NotificationInterval) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED] = true
            preferences[NOTIFICATION_INTERVAL] = interval.intervalMinutes
        }
    }

    override suspend fun disableNotifications() {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED] = false
        }
    }

    override suspend fun updateLastNotificationTime(timestamp: Long) {
        context.dataStore.edit { preferences ->
            preferences[LAST_NOTIFICATION_TIME] = timestamp
        }
    }
}