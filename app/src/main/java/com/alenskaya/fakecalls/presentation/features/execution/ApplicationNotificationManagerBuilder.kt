package com.alenskaya.fakecalls.presentation.features.execution

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object ApplicationNotificationManagerBuilder {

    const val CHANNEL_ID = "calls_notification_channel"
    const val CHANNEL_NAME = "Calls Notifications"

    fun build(context: Context): NotificationManagerCompat {
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
        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
    }
}
