package com.alenskaya.fakecalls.presentation.features.execution

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.CallExecutionActivity
import java.util.Date

/**
 * Creates fullscreen notification about incoming call.
 * Clicking on notification opens call execution screen.
 */
object IncomingCallNotificationBuilder {

    /**
     * Builds notification for call.
     * @param context - application context.
     * @param callExecutionParams - parameters of the call.
     */
    fun build(context: Context, callExecutionParams: CallExecutionParams): Notification {
        val fullScreenIntent = createIntent(context, callExecutionParams)

        return NotificationCompat.Builder(context, CallsNotificationManagerBuilder.CHANNEL_ID)
            .setSmallIcon(R.drawable.incoming_call_icon)
            .setContentTitle(context.getString(R.string.incoming_call_notification_title))
            .setAutoCancel(false)
            .setStyle(
                NotificationCompat.MessagingStyle("")
                    .setConversationTitle("Incoming call")
                    .addMessage(
                        NotificationCompat.MessagingStyle
                            .Message(
                                callExecutionParams.phone,
                                Date().time,
                                Person.Builder().setName(callExecutionParams.name).build()
                            )
                    )
            )
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(fullScreenIntent)
            .setFullScreenIntent(fullScreenIntent, true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setVibrate(LongArray(0))
            .build()
    }

    private fun createIntent(
        context: Context,
        callExecutionParams: CallExecutionParams
    ): PendingIntent {
        val fullScreenIntent = Intent(context, CallExecutionActivity::class.java).apply {
            putExtras(callExecutionParams.convertToBundle())
        }

        return PendingIntent.getActivity(
            context,
            0,
            fullScreenIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}
