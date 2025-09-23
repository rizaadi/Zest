package com.zephysus.core.repository

import com.zephysus.core.model.DarkThemeConfig
import com.zephysus.core.model.NotificationInterval
import com.zephysus.core.model.NotificationSettings
import com.zephysus.core.model.UserData
import com.zephysus.data.local.datastore.ZestPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val zestPreferencesDataSource: ZestPreferencesDataSource
) : UserDataRepository {
    override val userData: Flow<UserData> = zestPreferencesDataSource.userData

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) =
        zestPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) =
        zestPreferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)

    override suspend fun updateNotificationSettings(settings: NotificationSettings) =
        zestPreferencesDataSource.updateNotificationSettings(settings)

    override suspend fun enableNotifications(interval: NotificationInterval) =
        zestPreferencesDataSource.enableNotifications(interval)

    override suspend fun disableNotifications() =
        zestPreferencesDataSource.disableNotifications()

    override suspend fun updateLastNotificationTime(timestamp: Long) =
        zestPreferencesDataSource.updateLastNotificationTime(timestamp)
}