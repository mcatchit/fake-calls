package com.alenskaya.fakecalls.presentation.features.create

import com.alenskaya.fakecalls.presentation.UiState
import com.alenskaya.fakecalls.presentation.features.create.model.CreateCallScreenFormModel
import com.alenskaya.fakecalls.presentation.features.create.model.CreateCallScreenFormLabels

/**
 * Ui state of Create call screen
 */
data class CreateCallScreenState(

    /**
     * Screen labels
     */
    val formLabels: CreateCallScreenFormLabels,

    /**
     * Data of input fields
     */
    val formInput: CreateCallScreenFormModel,

    /**
     * Is screen loaded
     */
    val isInitialDataLoading: Boolean = false,

    /**
     * Is submit is in process
     */
    val isSubmitProcessing: Boolean = false
) : UiState {

    companion object {
        fun initial(): CreateCallScreenState {
            return CreateCallScreenState(
                CreateCallScreenFormLabels.initial(),
                CreateCallScreenFormModel.initial()
            )
        }
    }
}

/**
 * Copies CreateCallScreenState, but requests update only at formInput.
 * @param copyForm - action which executes copying of formInput.
 */
fun CreateCallScreenState.updateForm(
    copyForm: CreateCallScreenFormModel.() -> CreateCallScreenFormModel
): CreateCallScreenState {
    return this.copy(formInput = this.formInput.copyForm())
}
