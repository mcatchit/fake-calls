package com.alenskaya.fakecalls.presentation.execution.screen

import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.mvi.UiEvent

/**
 * Events of call execution screen.
 */
sealed interface CallExecutionScreenEvent : UiEvent

/**
 * Events of calling screen.
 */
sealed interface CallingScreenEvent : CallExecutionScreenEvent {
    class CallParametersLoaded(val callExecutionParams: CallExecutionParams) : CallingScreenEvent
    object DeclineCall : CallingScreenEvent
    object AcceptCall : CallingScreenEvent
}

/**
 * Events of accepted call screen.
 */
sealed interface OngoingCallScreenEvent : CallExecutionScreenEvent {
    class UpdateTimer(val time: String) : OngoingCallScreenEvent
    object EndCall : OngoingCallScreenEvent
}
