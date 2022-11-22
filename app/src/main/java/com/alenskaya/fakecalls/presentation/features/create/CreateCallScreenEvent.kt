package com.alenskaya.fakecalls.presentation.features.create

import com.alenskaya.fakecalls.presentation.UiEvent
import java.util.Date

/**
 * All possible create call screen events.
 */
sealed interface CreateCallScreenEvent : UiEvent {

    /**
     * Basic implementation of events which cause input changes.
     * @param T - type of changed value.
     * @property newValue - new input value.
     */
    sealed class InputChanged<T>(val newValue: T) : CreateCallScreenEvent

    /**
     * Name input was changed.
     */
    class NameChanged(newName: String) : InputChanged<String>(newName)

    /**
     * Phone input was changed.
     */
    class PhoneChanged(newPhone: String) : InputChanged<String>(newPhone)

    /**
     * New date was selected
     */
    class DateChanged(newDate: Date) : InputChanged<Date>(newDate)


    /**
     * Request to display date picker dialog
     */
    object ShowDatePicker : CreateCallScreenEvent

    /**
     * Intent to submit form
     */
    object SubmitForm : CreateCallScreenEvent

    /**
     * Submit is processing
     */
    object ProcessingSubmit : CreateCallScreenEvent

    /**
     * Request to navigate back
     */
    object ClickBack : CreateCallScreenEvent
}
