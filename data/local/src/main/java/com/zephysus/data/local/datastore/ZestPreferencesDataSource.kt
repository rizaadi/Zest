package com.zephysus.data.local.datastore

import androidx.datastore.core.DataStore
import com.zephysus.core.model.DarkThemeConfig
import com.zephysus.core.model.NotificationInterval
import com.zephysus.core.model.NotificationSettings
import com.zephysus.core.model.UserData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map

class ZestPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData = userPreferences.data.map {
        UserData(
            darkThemeConfig = when (it.darkThemeConfig) {
                null,
                DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                DarkThemeConfigProto.UNRECOGNIZED,
                DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM -> DarkThemeConfig.FOLLOW_SYSTEM

                DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT -> DarkThemeConfig.LIGHT
                DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
            },
            shouldHideOnboarding = it.shouldHideOnboarding,
            notificationSettings = NotificationSettings(
                isEnabled = it.notificationConfig?.enabled ?: false,
                interval = NotificationInterval.fromMinutes(
                    it.notificationConfig?.intervalMinutes
                        ?: NotificationInterval.ONE_HOUR.intervalMinutes
                ) ?: NotificationInterval.ONE_HOUR,
                lastNotificationTime = it.notificationConfig?.lastNotificationTime ?: 0L
            )
        )
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
    }

    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.shouldHideOnboarding = shouldHideOnboarding
            }
        }
    }

    suspend fun updateNotificationSettings(settings: NotificationSettings) {
        userPreferences.updateData {
            it.copy {
                notificationConfig = notificationConfigProto {
                    enabled = settings.isEnabled
                    intervalMinutes = settings.interval.intervalMinutes
                    lastNotificationTime = settings.lastNotificationTime
                }
            }
        }
    }

    suspend fun enableNotifications(interval: NotificationInterval) {
        userPreferences.updateData {
            it.copy {
                notificationConfig = notificationConfigProto {
                    enabled = true
                    intervalMinutes = interval.intervalMinutes
                }
            }
        }
    }

    suspend fun disableNotifications() {
        userPreferences.updateData {
            it.copy {
                notificationConfig = notificationConfigProto {
                    enabled = false
                }
            }
        }
    }

    suspend fun updateLastNotificationTime(timestamp: Long) {
        userPreferences.updateData {
            it.copy {
                notificationConfig = notificationConfigProto {
                    enabled = it.notificationConfig?.enabled ?: false
                    intervalMinutes = it.notificationConfig?.intervalMinutes
                        ?: NotificationInterval.ONE_HOUR.intervalMinutes
                    lastNotificationTime = timestamp
                }
            }
        }
    }
}
