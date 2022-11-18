package com.alenskaya.fakecalls.presentation.feature.calls

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Extracts day from date
 * TODO add extraction formats rules
 */
fun Date.extractDay(): String {
    return SimpleDateFormat("dd.MM", Locale.UK).format(this)
}

/**
 * Extracts time from date
 */
fun Date.extractTime(): String {
    return SimpleDateFormat("HH:mm", Locale.UK).format(this)
}
