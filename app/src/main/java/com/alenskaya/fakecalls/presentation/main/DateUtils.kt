package com.alenskaya.fakecalls.presentation.main

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Extracts year from date
 */
fun Date.extractYear(): Int {
    return getCalendar().get(Calendar.YEAR)
}

/**
 * Extracts day from date
 */
fun Date.extractDayWithMonth(): String {
    val scheme = if (isCurrentYear()) "dd.MM" else "dd.MM.yyyy"
    return SimpleDateFormat(scheme, Locale.UK).format(this)
}

/**
 * Extracts time from date
 */
fun Date.extractTime(): String {
    return SimpleDateFormat("HH:mm", Locale.UK).format(this)
}

/**
 * Extracts hour from date
 */
fun Date.extractHour(): Int {
    return getCalendar().get(Calendar.HOUR_OF_DAY)
}

/**
 * Extracts minute from date
 */
fun Date.extractMinute(): Int {
    return getCalendar().get(Calendar.MINUTE)
}

/**
 * Updates date with provided [hour] and [minute]
 */
fun Date.setHourAndMinute(hour: Int, minute: Int): Date {
    return getCalendar()
        .apply {
            time = this@setHourAndMinute
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        .time
}

/**
 * Sets date to 00:00:00
 */
fun Date.setToMidnight(): Date {
    return getCalendar()
        .apply {
            time = this@setToMidnight
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.MILLISECOND, 0)
        }
        .time
}

/**
 * Converts date to "dd.MM.yy HH:mm" pattern if its year equals current, "dd.MM HH:mm" - otherwise
 */
fun Date.convertToString(): String {
    val withYearPattern = "dd.MM.yy HH:mm"
    val withoutYearPattern = "dd.MM HH:mm"

    val finalPattern = if (extractYear() == getCurrentYear()) {
        withoutYearPattern
    } else {
        withYearPattern
    }

    return SimpleDateFormat(finalPattern, Locale.UK).format(this)
}

private fun Date.getCalendar(): Calendar = Calendar.getInstance().apply {
    time = this@getCalendar
}

private fun Date.isCurrentYear() = this.extractYear() == getCurrentYear()

private fun getCurrentYear(): Int {
    return Date().extractYear()
}
