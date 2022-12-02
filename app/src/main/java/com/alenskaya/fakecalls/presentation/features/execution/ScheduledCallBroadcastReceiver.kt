package com.alenskaya.fakecalls.presentation.features.execution

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.alenskaya.fakecalls.domain.calls.MarkCallAsCompletedUseCase
import com.alenskaya.fakecalls.presentation.CallsDataChangedNotifier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Broadcast receiver for scheduled calls.
 * Called when it's time to execute a scheduled call.
 * Uses notificationManager to pass notification about an incoming call.
 *
 * Also it executes [markCallAsCompletedUseCase] to mark call as completed
 * and notifies listeners of [callsDataChangedNotifier] about performed changes.
 */
@AndroidEntryPoint
class ScheduledCallBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationManagerCompat: NotificationManagerCompat

    @Inject
    lateinit var markCallAsCompletedUseCase: MarkCallAsCompletedUseCase

    @Inject
    lateinit var callsDataChangedNotifier: CallsDataChangedNotifier

    private val scope = CoroutineScope(SupervisorJob())

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras ?: error("Broadcast bundle cannot be null")
        val callExecutionParams = bundle.extractCallExecutionParams()

        notificationManagerCompat.notify(
            NOTIFICATION_ID,
            IncomingCallNotificationBuilder.build(context, callExecutionParams)
        )

        doAsync(scope) {
            markCallAsCompletedUseCase(callExecutionParams.callId).collect {
                callsDataChangedNotifier.callsDataChanged()
            }
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}
