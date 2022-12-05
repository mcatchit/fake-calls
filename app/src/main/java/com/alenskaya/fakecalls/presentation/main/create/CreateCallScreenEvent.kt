package com.alenskaya.fakecalls.presentation.main.create

import com.alenskaya.fakecalls.presentation.mvi.UiEvent
import com.alenskaya.fakecalls.presentation.main.create.model.CreateCallScreenFormLabels
import com.alenskaya.fakecalls.presentation.main.create.model.CreateCallScreenFormModel
import java.util.Date

/**
 * All possible create call screen events.
 */
sealed interface CreateCallScreenEvent : UiEvent {

    /**
     * Screen mode is loaded.
     * @property labels - screen labels values.
     */
    class ModeLoaded(val labels: CreateCallScreenFormLabels) : CreateCallScreenEvent

    /**
     * Screen form is loading.
     */
    object FormLoading : CreateCallScreenEvent

    /**
     * Screen form is loaded.
     * @property formModel - data of screen form.
     */
    data class FormLoaded(val formModel: CreateCallScreenFormModel) : CreateCallScreenEvent

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
     * New date was selected.
     */
    class DateChanged(newDate: Date) : InputChanged<Date>(newDate)

    /**
     * Request to display date picker dialog.
     */
    object ShowDatePicker : CreateCallScreenEvent

    /**
     * Intent to submit form.
     */
    object SubmitForm : CreateCallScreenEvent

    /**
     * Submit is processing.
     */
    object ProcessingSubmit : CreateCallScreenEvent

    /**
     * Submit was unsuccessful
     */
    object UnsuccessfulSubmit: CreateCallScreenEvent

    /**
     * Request to navigate back.
     */
    object ClickBack : CreateCallScreenEvent
}