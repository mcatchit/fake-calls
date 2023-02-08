package com.alenskaya.fakecalls.presentation.execution

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

/**
 * Allows to show and hide notification.
 * Creates notification channel for it.
 */
class CallsNotificationManager(
    context: Context
) {
    private val notificationManagerCompat = buildManagerCompat(context)

    /**
     * Shows [notification]
     */
    fun showNotification(notification: Notification) {
        notificationManagerCompat.notify(
            NOTIFICATION_ID,
            notification
        )
    }

    /**
     * Hides notification.
     */
    fun hideNotification() {
        notificationManagerCompat.cancel(NOTIFICATION_ID)
    }

    private fun buildManagerCompat(context: Context): NotificationManagerCompat {
        return NotificationManagerCompat.from(context).apply {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(getChannel())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getChannel() = NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        enableVibration(true)
        setSound(CALL_SOUND, CALL_AUDIO_ATTRIBUTES)
        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
    }

    companion object {
        private const val CHANNEL_NAME = "Calls Notifications"
        private const val NOTIFICATION_ID = 1
    }
}