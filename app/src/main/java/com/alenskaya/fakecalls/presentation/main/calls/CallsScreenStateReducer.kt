package com.alenskaya.fakecalls.presentation.main.calls

import com.alenskaya.fakecalls.presentation.mvi.Reducer
import kotlinx.coroutines.CoroutineScope

/**
 * Manages CallsScreen state
 */
class CallsScreenStateReducer(
    viewModelScope: CoroutineScope,
    initialState: CallsScreenState,
    private val loadCallsAction: () -> Unit
) :
    Reducer<CallsScreenState, CallsScreenEvent, CallsScreenOneTimeUiEffect>(
        viewModelScope, initialState
    ) {

    override fun processEvent(oldState: CallsScreenState, event: CallsScreenEvent) {
        if (event is CallsScreenEvent.CallsLoading) {
            loadCallsAction()
        }
        setState(oldState.reduce(event))
    }

    private fun CallsScreenState.reduce(event: CallsScreenEvent): CallsScreenState {
        return when (event) {
            CallsScreenEvent.CallsLoading -> copy(isLoading = true)
            is CallsScreenEvent.CallsLoaded -> copy(
                isLoading = false,
                scheduledCalls = event.scheduledCalls,
                completedCalls = event.completedCalls,
            )
            CallsScreenEvent.CallsLoadedEmpty -> copy(
                isLoading = false,
                message = "No calls scheduled or completed yet"
            )
            CallsScreenEvent.CallsNotLoaded -> copy(
                isLoading = false,
                message = "Something went wrong. Cannot load your calls"
            )
        }
    }
}
