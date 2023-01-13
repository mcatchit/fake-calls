package com.alenskaya.fakecalls.presentation.execution.screen.ongoing

import com.alenskaya.fakecalls.util.Converter

/**
 * Converts number of passed seconds to 'mm:ss' format.
 */
object OngoingCallSecondsToStringConverter : Converter<Int, String> {

    private const val SECONDS_IN_MINUTE = 60

    override fun convert(input: Int): String {
        val seconds = input % SECONDS_IN_MINUTE
        val minutes = input / SECONDS_IN_MINUTE

        return "${format(minutes)}:${format(seconds)}"
    }

    private fun format(value: Int) = if (value < 10) "0$value" else value.toString()
}
