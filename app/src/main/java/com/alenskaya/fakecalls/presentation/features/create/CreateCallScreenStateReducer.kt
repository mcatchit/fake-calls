package com.alenskaya.fakecalls.presentation.features.create

import com.alenskaya.fakecalls.presentation.Reducer
import com.alenskaya.fakecalls.presentation.features.create.model.CreateCallScreenFormModel
import com.alenskaya.fakecalls.presentation.features.create.model.DateTimePickerData
import com.alenskaya.fakecalls.presentation.features.create.model.TimePickerData
import com.alenskaya.fakecalls.presentation.features.create.model.isFilled
import com.alenskaya.fakecalls.presentation.features.extractHour
import com.alenskaya.fakecalls.presentation.features.extractMinute
import kotlinx.coroutines.CoroutineScope
import java.util.Date

/**
 * Processes updates of Create call screen states
 * @property navigateBackCallback - requests navigate back action.
 * @property submitFormCallBack - requests action of call saving.
 */
class CreateCallScreenStateReducer(
    viewModelScope: CoroutineScope,
    initialState: CreateCallScreenState,
    private val navigateBackCallback: () -> Unit,
    private val submitFormCallBack: (CreateCallScreenFormModel) -> Unit
) : Reducer<CreateCallScreenState, CreateCallScreenEvent, CreateCallScreenOneTimeUiEffect>(
    viewModelScope,
    initialState
) {

    override fun processEvent(oldState: CreateCallScreenState, event: CreateCallScreenEvent) {
        when (event) {
            is CreateCallScreenEvent.InputChanged<*> -> processInputChangedEvent(oldState, event)
            is CreateCallScreenEvent.ShowDatePicker -> showDatePicker(oldState.formInput)
            is CreateCallScreenEvent.SubmitForm -> submitForm(oldState.formInput)
            is CreateCallScreenEvent.ProcessingSubmit -> setState(oldState.copy(isSubmitProcessing = true))
            is CreateCallScreenEvent.ClickBack -> navigateBackCallback()
        }
    }

    private fun processInputChangedEvent(
        oldState: CreateCallScreenState,
        event: CreateCallScreenEvent.InputChanged<*>
    ) {
        val newState = when (event) {
            is CreateCallScreenEvent.NameChanged -> oldState.updateForm { copy(name = event.newValue) }
            is CreateCallScreenEvent.PhoneChanged -> oldState.updateForm { copy(phone = event.newValue) }
            is CreateCallScreenEvent.DateChanged -> oldState.updateForm { copy(date = event.newValue) }
        }

        setState(newState)
    }

    private fun showDatePicker(formInput: CreateCallScreenFormModel) {
        val selectedDate = formInput.date ?: Date()
        val dateTimePickerData = DateTimePickerData(
            minDate = Date().time,
            selectedDate = selectedDate.time,
            timePickerData = TimePickerData(
                selectedHour = selectedDate.extractHour(),
                selectedMinute = selectedDate.extractMinute()
            )
        )
        sendOneTimeEffect(CreateCallScreenOneTimeUiEffect.ShowDatePicker(dateTimePickerData))
    }

    private fun submitForm(formInput: CreateCallScreenFormModel) {
        if (formInput.isFilled()) {
            submitFormCallBack(formInput)
        } else {
            sendOneTimeEffect(CreateCallScreenOneTimeUiEffect.ShowToast("Please, fill in all fields")) //FIXME
        }
    }
}