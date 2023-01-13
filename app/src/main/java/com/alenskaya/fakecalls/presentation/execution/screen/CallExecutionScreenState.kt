package com.alenskaya.fakecalls.presentation.execution.screen

import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.mvi.UiState

/**
 * State of call execution screen.
 */
sealed interface CallExecutionScreenState : UiState {

    /**
     * State is not set.
     */
    object Default : CallExecutionScreenState

    /**
     * Call has not been accepted yet.
     * @param callExecutionParams - parameters of call.
     * @param isDeclined - true if call is declined, false if still calling.
     */
    data class Calling(
        val callExecutionParams: CallExecutionParams,
        val isDeclined: Boolean
    ) : CallExecutionScreenState {

        companion object {
            fun initial(callExecutionParams: CallExecutionParams) =
                Calling(callExecutionParams, false)
        }
    }

    /**
     * Call has been accepted.
     * @param callExecutionParams - parameters of call.
     * @param time - time passed since accepting call.
     * @param isCompleted - true if call is ended, false otherwise.
     */
    data class OngoingCall(
        val callExecutionParams: CallExecutionParams,
        val time: String,
        val isCompleted: Boolean
    ) : CallExecutionScreenState {

        companion object {
            fun initial(callExecutionParams: CallExecutionParams) =
                OngoingCall(callExecutionParams, "", false)
        }
    }
}
