package com.alenskaya.fakecalls.presentation.execution

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
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
        val person = Person.Builder().setName(callExecutionParams.name).build()

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.incoming_call_icon)
            .setContentTitle(context.getString(R.string.incoming_call_notification_title))
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.MessagingStyle(person)
                    .setConversationTitle(context.getString(R.string.incoming_call_notification_title))
                    .addMessage(
                        NotificationCompat.MessagingStyle
                            .Message(
                                callExecutionParams.phone,
                                Date().time,
                                person
                            )
                    )
            )
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setTimeoutAfter(CALL_NOTIFICATION_TIMEOUT)
            .setContentIntent(fullScreenIntent)
            .setFullScreenIntent(fullScreenIntent, true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setSound(CALL_SOUND)
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
