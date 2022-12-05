package com.alenskaya.fakecalls.presentation.execution

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import java.util.Date

/**
 * Requests [alarmManager] to execute a call notification at specified time.
 * @property context - application context.
 * @property alarmManager - application alarm manager.
 */
class CallsScheduler(
    private val context: Context,
    private val alarmManager: AlarmManager
) {

    /**
     * Schedules a call with [callExecutionParams] at passed time [whenExecute].
     */
    fun scheduleCall(callExecutionParams: CallExecutionParams, whenExecute: Date) {
        val plannedCallIntent = createExactAlarmIntent(callExecutionParams)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            whenExecute.time,
            plannedCallIntent
        )
    }

    private fun createExactAlarmIntent(callExecutionParams: CallExecutionParams): PendingIntent {
        val intent = Intent(context, ScheduledCallBroadcastReceiver::class.java).apply {
            putExtras(callExecutionParams.convertToBundle())
        }

        return PendingIntent.getBroadcast(
            context,
            PLAN_CALL_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        private const val PLAN_CALL_REQUEST_CODE = 1239
    }
}
