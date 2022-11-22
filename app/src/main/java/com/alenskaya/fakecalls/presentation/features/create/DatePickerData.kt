package com.alenskaya.fakecalls.presentation.features.create

import java.util.Date

/**
 * Data for displaying date picker.
 */
data class DatePickerData(

    /**
     * Left bound of selectable dates.
     */
    val minDate: Date,

    /**
     * Currently selected date
     */
    val selectedDate: Date
)
