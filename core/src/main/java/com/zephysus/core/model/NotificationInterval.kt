package com.zephysus.core.model

enum class NotificationInterval(
    val displayName: String,
    val intervalMinutes: Long
) {
    FIFTEEN_MINUTES("15 minutes", 15),  // Minimum for PeriodicWork
    THIRTY_MINUTES("30 minutes", 30),
    ONE_HOUR("1 hour", 60);

    companion object {
        fun fromMinutes(minutes: Long): NotificationInterval? {
            return entries.find { it.intervalMinutes == minutes }
        }
    }
}