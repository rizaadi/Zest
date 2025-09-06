package com.zephysus.zest.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.zephysus.core.di.LocalRepository
import com.zephysus.core.repository.QuoteRepository
import com.zephysus.zest.MainActivity
import com.zephysus.zest.R
import com.zephysus.zest.di.ChildWorkerFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlin.random.Random

/**
 * Worker for showing featured quote notifications
 * Uses Hilt for dependency injection.
 */
@HiltWorker
class FeaturedQuoteNotificationWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters,
    @LocalRepository private val quoteRepository: QuoteRepository
) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "featured_quote_notification"
        const val CHANNEL_ID = "featured_quotes_channel"
        const val NOTIFICATION_ID = 1001
    }

    override suspend fun doWork(): Result {
        return try {
            Log.d("FEATURED_QUOTES", "Quotes: dowork")
            Result.success()
            val result = quoteRepository.getAllFeaturedQuotes().first()

            result.onSuccess { quotes ->

                if (quotes.isNotEmpty()) {
                    val randomQuote = quotes[Random.nextInt(quotes.size)]
                    createNotificationChannel()
                    showNotification(randomQuote.title, randomQuote.author)
                    return Result.success()
                } else {
                    // No featured quotes available
                    return Result.failure()
                }
            }.onFailure {
                // Failed to get quotes from repository
                return Result.retry()
            }

            // This should never be reached
            Result.failure()
        } catch (er: Exception) {
            Log.e("FEATURED_QUOTES", "Error fetching featured quotes", er)
            Result.retry()
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID, "Featured Quotes", NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notifications for featured quotes"
            enableLights(true)
            enableVibration(true)
        }

        val notificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification(quoteTitle: String, author: String) {
        val intent = Intent(appContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            appContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification).setContentTitle("Featured Quote")
            .setContentText("\"$quoteTitle\" - $author")
            .setStyle(NotificationCompat.BigTextStyle().bigText("\"$quoteTitle\" - $author"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent)
            .setAutoCancel(true).build()

        val notificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    @AssistedFactory
    interface Factory : ChildWorkerFactory {
        override fun create(
            appContext: Context, params: WorkerParameters
        ): FeaturedQuoteNotificationWorker
    }
}