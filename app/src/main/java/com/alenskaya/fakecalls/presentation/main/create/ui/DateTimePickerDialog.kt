package com.alenskaya.fakecalls.presentation.main.create.ui

import android.os.Parcel
import androidx.fragment.app.DialogFragment
import com.alenskaya.fakecalls.presentation.DialogsDisplayer
import com.alenskaya.fakecalls.presentation.main.create.model.DateTimePickerData
import com.alenskaya.fakecalls.presentation.main.create.model.TimePickerData
import com.alenskaya.fakecalls.presentation.main.setHourAndMinute
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.Date

/**
 * Displays date-time picker dialog.
 * @property dialogsDisplayer - application dialogs displayer.
 */
class DateTimePickerDialog(
    private val datePickerTitle: String,
    private val timePickerTitle: String,
    private val dialogsDisplayer: DialogsDisplayer
) {

    /**
     * Launches date-time picker dialogs.
     * @param data - data to use and display in pickers.
     * @param datePickedCallback - callback which called when user has selected date and time.
     */
    fun pickDate(data: DateTimePickerData, datePickedCallback: (Date) -> Unit) {
        createDatePickerDialog(datePickerTitle, data) { selectedDate ->
            pickTime(timePickerTitle, selectedDate, data.timePickerData, datePickedCallback)
        }.showWithDisplayer()
    }

    private fun createDatePickerDialog(
        title: String,
        data: DateTimePickerData,
        onPositiveButtonClickListener: (Long) -> Unit
    ): DialogFragment {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText(title)
            .setSelection(data.selectedDate)
            .setCalendarConstraints(createCalendarConstraints(data.minDate))
            .build()
            .apply {
                addOnPositiveButtonClickListener(onPositiveButtonClickListener)
            }
    }

    private fun pickTime(
        title: String,
        selectedDate: Long,
        timePickerData: TimePickerData,
        datePickedCallback: (Date) -> Unit
    ) {
        createTimePickerDialog(
            title, selectedDate, timePickerData, datePickedCallback
        ).showWithDisplayer()
    }

    private fun createTimePickerDialog(
        title: String,
        selectedDate: Long,
        timePickerData: TimePickerData,
        datePickedCallback: (Date) -> Unit
    ): DialogFragment {
        return MaterialTimePicker.Builder()
            .setTitleText(title)
            .setHour(timePickerData.selectedHour)
            .setMinute(timePickerData.selectedMinute)
            .build().apply {
                addOnPositiveButtonClickListener {
                    datePickedCallback(
                        getFullSelectedDate(selectedDate, hour, minute)
                    )
                }
            }
    }

    private fun createCalendarConstraints(minDate: Long) = CalendarConstraints.Builder()
        .setValidator(createDateValidator(minDate)).build()

    private fun createDateValidator(minDate: Long) = object : CalendarConstraints.DateValidator {

        override fun isValid(date: Long): Boolean {
            return date >= minDate
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
        }
    }

    private fun getFullSelectedDate(selectedDate: Long, selectedHour: Int, selectedMinute: Int) =
        Date(selectedDate).setHourAndMinute(selectedHour, selectedMinute)

    private fun DialogFragment.showWithDisplayer() {
        dialogsDisplayer.showDialog(this)
    }
}