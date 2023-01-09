package com.alenskaya.fakecalls.presentation.execution.screen

import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.mvi.UiState

data class CallExecutionScreenState(
    val callExecutionParams: CallExecutionParams,
    val isDeclined: Boolean
) : UiState {

    companion object {
        fun initial() =
            CallExecutionScreenState(CallExecutionParams(0, "", "", "", 0), false)
    }
}
