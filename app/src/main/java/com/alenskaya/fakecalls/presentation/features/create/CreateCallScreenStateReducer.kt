package com.alenskaya.fakecalls.presentation.features.create

import com.alenskaya.fakecalls.presentation.Reducer
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
    private val submitFormCallBack: (CreateCallScreenFormInput) -> Unit
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

    private fun showDatePicker(formInput: CreateCallScreenFormInput) {
        val selectedDate = formInput.date ?: Date()
        val datePickerData = DatePickerData(Date(), selectedDate)
        sendOneTimeEffect(CreateCallScreenOneTimeUiEffect.ShowDatePicker(datePickerData))
    }

    private fun submitForm(formInput: CreateCallScreenFormInput) {
        if (formInput.isFilled()) {
            submitFormCallBack(formInput)
        } else {
            sendOneTimeEffect(CreateCallScreenOneTimeUiEffect.ShowToast("Please, fill in all fields")) //FIXME
        }
    }
}
