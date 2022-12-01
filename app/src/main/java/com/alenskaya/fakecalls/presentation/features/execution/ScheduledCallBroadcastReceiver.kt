package com.alenskaya.fakecalls.presentation.features.execution

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.alenskaya.fakecalls.presentation.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ScheduledCallBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationManagerCompat: NotificationManagerCompat

    override fun onReceive(context: Context, intent: Intent) {
        context.showToast("Incoming call")

        notificationManagerCompat.notify(NOTIFICATION_ID, IncomingCallNotificationBuilder.build(context))

        context.showToast("Incoming call executing")
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}
