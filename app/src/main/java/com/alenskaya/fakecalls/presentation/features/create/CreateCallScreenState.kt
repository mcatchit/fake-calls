package com.alenskaya.fakecalls.presentation.features.create

import com.alenskaya.fakecalls.presentation.UiState

/**
 * Ui state of Create call screen
 */
data class CreateCallScreenState(

    /**
     * Screen title
     */
    val title: String,

    /**
     * Save button text
     */
    val buttonText: String,

    /**
     * Data of input fields
     */
    val formInput: CreateCallScreenFormInput,

    /**
     * Is screen loaded
     */
    val isInitialDataLoading: Boolean = true,

    /**
     * Is submit is in process
     */
    val isSubmitProcessing: Boolean = false
) : UiState {

    companion object {
        fun initial(): CreateCallScreenState {
            return CreateCallScreenState("Create new call", "Create", CreateCallScreenFormInput())
        }
    }
}

/**
 * Copies CreateCallScreenState, but requests update only at formInput.
 * @param copyForm - action which executes copying of formInput.
 */
fun CreateCallScreenState.updateForm(
    copyForm: CreateCallScreenFormInput.() -> CreateCallScreenFormInput
): CreateCallScreenState {
    return this.copy(formInput = this.formInput.copyForm())
}
