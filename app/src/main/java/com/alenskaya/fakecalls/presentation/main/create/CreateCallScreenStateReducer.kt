package com.alenskaya.fakecalls.presentation.main.create

import com.alenskaya.fakecalls.presentation.mvi.Reducer
import com.alenskaya.fakecalls.presentation.main.create.model.CreateCallScreenFormModel
import com.alenskaya.fakecalls.presentation.main.create.model.DateTimePickerData
import com.alenskaya.fakecalls.presentation.main.create.model.TimePickerData
import com.alenskaya.fakecalls.presentation.main.create.model.isFilled
import com.alenskaya.fakecalls.presentation.main.extractHour
import com.alenskaya.fakecalls.presentation.main.extractMinute
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
            is CreateCallScreenEvent.ModeLoaded -> setState(oldState.copy(formLabels = event.labels))
            is CreateCallScreenEvent.FormLoading -> setState(oldState.copy(isInitialDataLoading = true))
            is CreateCallScreenEvent.FormLoaded -> setState(
                oldState.copy(
                    isInitialDataLoading = false,
                    formInput = event.formModel
                )
            )
            is CreateCallScreenEvent.InputChanged<*> -> processInputChangedEvent(oldState, event)
            is CreateCallScreenEvent.ShowDatePicker -> showDatePicker(oldState.formInput)
            is CreateCallScreenEvent.SubmitForm -> submitForm(oldState.formInput)
            is CreateCallScreenEvent.PermissionToScheduleACallIsNotGranted -> processNotGrantedPermission()
            is CreateCallScreenEvent.ProcessingSubmit -> setState(oldState.copy(isSubmitProcessing = true))
            is CreateCallScreenEvent.UnsuccessfulSubmit -> processUnsuccessfulSubmit(oldState)
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
        checkFormInput(formInput) {
            validateDate(formInput.date ?: error("Valid date cannot be null")) {
                submitFormCallBack(formInput)
            }
        }
    }

    private fun processNotGrantedPermission() {
        showToast("Please, give the application permission to set alarms") //FIXME
    }

    private fun processUnsuccessfulSubmit(oldState: CreateCallScreenState) {
        setState(oldState.copy(isSubmitProcessing = false))
        showToast("Oooops.. Please, try again") //FIXME
    }

    private fun checkFormInput(formInput: CreateCallScreenFormModel, doIfValid: () -> Unit) {
        if (formInput.isFilled()) {
            doIfValid()
        } else {
            showToast("Please, fill in all fields") //FIXME
        }
    }

    private fun validateDate(userInputDate: Date, doIfValid: () -> Unit) {
        if (userInputDate > Date()) {
            doIfValid()
        } else {
            showToast("Date is not valid") //FIXME
        }
    }

    private fun showToast(message: String) {
        sendOneTimeEffect(CreateCallScreenOneTimeUiEffect.ShowToast(message))
    }
}
