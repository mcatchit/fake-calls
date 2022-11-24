package com.alenskaya.fakecalls.presentation.features.create.model

/**
 * Data for date picker displaying.
 */
data class DateTimePickerData(

    /**
     * Left bound of selectable dates.
     */
    val minDate: Long,

    /**
     * Currently selected date
     */
    val selectedDate: Long,

    /**
     * Currently selected time
     */
    val timePickerData: TimePickerData
)
