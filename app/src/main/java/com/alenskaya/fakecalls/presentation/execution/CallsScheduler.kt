package com.alenskaya.fakecalls.presentation.execution

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
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
     * @return request code of call pending intent.
     */
    fun scheduleCall(callExecutionParams: CallExecutionParams, whenExecute: Date) {
        val plannedCallIntent = createExactAlarmIntent(callExecutionParams)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            whenExecute.time,
            plannedCallIntent
        )
    }

    /**
     * Cancels a call with given parameters.
     * @param callExecutionParams - params of cancelling call.
     * @return true if call was cancelled, false if something went wrong.
     */
    fun cancelCall(callExecutionParams: CallExecutionParams): Boolean {
        return try {
            val intent = createExactAlarmIntent(callExecutionParams)
            alarmManager.cancel(intent)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun createExactAlarmIntent(
        callExecutionParams: CallExecutionParams
    ): PendingIntent {
        val intent = Intent(context, ScheduledCallBroadcastReceiver::class.java).apply {
            putExtras(callExecutionParams.convertToBundle())
        }

        return PendingIntent.getBroadcast(
            context,
            callExecutionParams.requestCode, //Request code must be unique!
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}
