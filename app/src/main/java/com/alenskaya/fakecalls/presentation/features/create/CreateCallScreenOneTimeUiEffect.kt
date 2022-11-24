package com.alenskaya.fakecalls.presentation.features.create

import com.alenskaya.fakecalls.presentation.OneTimeUiEffect
import com.alenskaya.fakecalls.presentation.features.create.model.DateTimePickerData

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
