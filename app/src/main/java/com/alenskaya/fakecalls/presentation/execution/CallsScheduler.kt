package com.alenskaya.fakecalls.presentation.execution

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import java.util.Date
import java.util.UUID

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
     * Checks permission to schedule a call.
     */
    fun hasPermissionToScheduleACall(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

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
            UUID.randomUUID().hashCode(), //Request code must be unique!
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}
