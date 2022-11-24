package com.alenskaya.fakecalls.presentation.features

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
 * TODO add extraction formats rules
 */
fun Date.extractDayWithMonth(): String {
    return SimpleDateFormat("dd.MM", Locale.UK).format(this)
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

private fun getCurrentYear(): Int {
    return Date().extractYear()
}
