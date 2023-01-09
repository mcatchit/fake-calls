package com.alenskaya.fakecalls.presentation.execution.screen

import com.alenskaya.fakecalls.presentation.mvi.OneTimeUiEffect
import com.alenskaya.fakecalls.presentation.mvi.Reducer
import kotlinx.coroutines.CoroutineScope

class CallExecutionScreenStateReducer(
    viewModelScope: CoroutineScope,
    initialState: CallExecutionScreenState,
    private val cancelCallAction: CancelCallAction,
) : Reducer<CallExecutionScreenState, CallExecutionScreenEvent, OneTimeUiEffect>(viewModelScope, initialState) {

    override fun processEvent(oldState: CallExecutionScreenState, event: CallExecutionScreenEvent) {
        when (event) {
            is CallExecutionScreenEvent.CallParametersLoaded -> setState(oldState.copy(callExecutionParams = event.callExecutionParams))
            CallExecutionScreenEvent.AcceptCall -> {}
            CallExecutionScreenEvent.DeclineCall -> {
                setState(oldState.copy(isDeclined = true))
                cancelCallAction()
            }
        }
    }
}
