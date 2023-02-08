package com.alenskaya.fakecalls.presentation.execution

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.alenskaya.fakecalls.domain.calls.MarkCallAsCompletedUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

/**
 * Broadcast receiver for scheduled calls.
 * Called when it's time to execute a scheduled call.
 * Uses notificationManager to pass notification about an incoming call.
 *
 * Also it executes [markCallAsCompletedUseCase] to mark call as completed.
 */
@AndroidEntryPoint
class ScheduledCallBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var callsNotificationManager: CallsNotificationManager

    @Inject
    lateinit var markCallAsCompletedUseCase: MarkCallAsCompletedUseCase

    private val scope = CoroutineScope(SupervisorJob())

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras ?: error("Broadcast bundle cannot be null")
        val callExecutionParams = bundle.extractCallExecutionParams()

        callsNotificationManager.showNotification(
            IncomingCallNotificationBuilder.build(context, callExecutionParams)
        )

        doAsync(scope) {
            markCallAsCompletedUseCase(callExecutionParams.callId).collect {}
        }
    }
}
