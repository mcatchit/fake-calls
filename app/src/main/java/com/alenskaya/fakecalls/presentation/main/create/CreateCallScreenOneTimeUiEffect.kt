package com.alenskaya.fakecalls.presentation.main.create

import com.alenskaya.fakecalls.presentation.mvi.OneTimeUiEffect
import com.alenskaya.fakecalls.presentation.main.create.model.DateTimePickerData

/**
 * All possible Create call screen one time ui effects.
 */
sealed class CreateCallScreenOneTimeUiEffect : OneTimeUiEffect {

    /**
     * Toast should be displayed.
     * @property message - toast message.
     */
    class ShowToast(val message: String) : CreateCallScreenOneTimeUiEffect()

    /**
     * Date picker dialog should be shown.
     * @property dateTimePickerData - data for date picker.
     */
    class ShowDatePicker(val dateTimePickerData: DateTimePickerData) :
        CreateCallScreenOneTimeUiEffect()
}
