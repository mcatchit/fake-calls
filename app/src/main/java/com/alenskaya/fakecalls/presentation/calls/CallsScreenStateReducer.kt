package com.alenskaya.fakecalls.presentation.calls

import com.alenskaya.fakecalls.presentation.Reducer
import kotlinx.coroutines.CoroutineScope

/**
 * Manages CallsScreen state
 */
class CallsScreenStateReducer(viewModelScope: CoroutineScope, initialState: CallsScreenState) :
    Reducer<CallsScreenState, CallsScreenEvent, CallsScreenOneTimeUiEffect>(
        viewModelScope, initialState
    ) {

    override fun processEvent(oldState: CallsScreenState, event: CallsScreenEvent) {
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
