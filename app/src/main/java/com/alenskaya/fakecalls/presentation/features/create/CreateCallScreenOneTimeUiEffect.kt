package com.alenskaya.fakecalls.presentation.features.create

import com.alenskaya.fakecalls.presentation.OneTimeUiEffect

/**
 * All possible Create call screen one time ui effects.
 */
interface CreateCallScreenOneTimeUiEffect : OneTimeUiEffect {

    /**
     * Toast should be displayed.
     * @property message - toast message.
     */
    data class ShowToast(val message: String) : CreateCallScreenOneTimeUiEffect

    /**
     * Date picker dialog should be shown.
     * @property datePickerData - data for date picker.
     */
    data class ShowDatePicker(val datePickerData: DatePickerData) : CreateCallScreenOneTimeUiEffect
}
