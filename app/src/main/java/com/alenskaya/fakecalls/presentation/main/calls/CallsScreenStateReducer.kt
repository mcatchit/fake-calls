package com.alenskaya.fakecalls.presentation.main.calls

import com.alenskaya.fakecalls.presentation.mvi.Reducer
import kotlinx.coroutines.CoroutineScope

/**
 * Manages CallsScreen state
 */
class CallsScreenStateReducer(
    viewModelScope: CoroutineScope,
    initialState: CallsScreenState,
    private val loadCallsAction: () -> Unit,
    private val openRepeatCallScreen: (Int) -> Unit
) :
    Reducer<CallsScreenState, CallsScreenEvent, CallsScreenOneTimeUiEffect>(
        viewModelScope, initialState
    ) {

    override fun processEvent(oldState: CallsScreenState, event: CallsScreenEvent) {
        when (event) {
            CallsScreenEvent.CallsLoading -> processScreenLoading(oldState)
            is CallsScreenEvent.CallsLoaded -> setState(
                oldState.copy(
                    isLoading = false,
                    scheduledCalls = event.scheduledCalls,
                    completedCalls = event.completedCalls,
                )
            )
            CallsScreenEvent.CallsLoadedEmpty -> setState(
                oldState.copy(
                    isLoading = false,
                    message = "No calls scheduled or completed yet"
                )
            )
            CallsScreenEvent.CallsNotLoaded -> setState(
                oldState.copy(
                    isLoading = false,
                    message = "Something went wrong. Cannot load your calls"
                )
            )
            is CallsScreenEvent.RepeatCall -> openRepeatCallScreen(event.callId)
        }
    }

    private fun processScreenLoading(oldState: CallsScreenState) {
        setState(oldState.copy(isLoading = true))
        loadCallsAction()
    }
}
