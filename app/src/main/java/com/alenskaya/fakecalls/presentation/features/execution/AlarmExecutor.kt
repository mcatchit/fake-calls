package com.alenskaya.fakecalls.presentation.features.execution

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.alenskaya.fakecalls.presentation.features.extractHour
import com.alenskaya.fakecalls.presentation.features.extractMinute
import com.alenskaya.fakecalls.presentation.features.setHourAndMinute
import java.util.Date

class AlarmExecutor(
    private val context: Context,
    private val alarmManager: AlarmManager
) {

    fun setExactAlarmSetExactAndAllowWhileIdle() {
        val current = Date()
        val minute = current.extractMinute() + 1
        val hour = current.extractHour()

        val new = current.setHourAndMinute(hour, minute)
        val triggerAtMillis = new.time

        val pendingIntent = createExactAlarmIntent()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    private fun createExactAlarmIntent(): PendingIntent {
        val intent = Intent(context, ScheduledCallBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            1111,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}
