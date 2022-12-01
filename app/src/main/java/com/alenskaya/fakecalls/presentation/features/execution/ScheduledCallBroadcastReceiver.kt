package com.alenskaya.fakecalls.presentation.features.execution

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Broadcast receiver for scheduled calls.
 * Called when it's time to execute a scheduled call.
 * Uses notificationManager to pass notification about an incoming call.
 */
@AndroidEntryPoint
class ScheduledCallBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationManagerCompat: NotificationManagerCompat

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras ?: error("Broadcast bundle cannot be null")

        notificationManagerCompat.notify(
            NOTIFICATION_ID,
            IncomingCallNotificationBuilder.build(context, bundle.extractCallExecutionParams())
        )
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}
