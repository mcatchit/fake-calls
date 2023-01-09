package com.alenskaya.fakecalls.presentation.execution.screen

import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.mvi.UiEvent

sealed interface CallExecutionScreenEvent : UiEvent {
    object DeclineCall : CallExecutionScreenEvent
    object AcceptCall : CallExecutionScreenEvent
    class CallParametersLoaded(val callExecutionParams: CallExecutionParams) : CallExecutionScreenEvent
}
