package com.alenskaya.fakecalls.presentation.execution.screen

import com.alenskaya.fakecalls.presentation.mvi.OneTimeUiEffect
import com.alenskaya.fakecalls.presentation.mvi.Reducer
import kotlinx.coroutines.CoroutineScope

/**
 * Manages and updates execution screen state.
 * @param endCallAction - action to end call.
 * @param startTimer - action to start timer.
 * @param stopTimer - action to stop timer.
 */
class CallExecutionScreenStateReducer(
    viewModelScope: CoroutineScope,
    initialState: CallExecutionScreenState,
    private val endCallAction: CancelCallAction,
    private val startTimer: () -> Unit,
    private val stopTimer: () -> Unit,
) : Reducer<CallExecutionScreenState, CallExecutionScreenEvent, OneTimeUiEffect>(
    viewModelScope,
    initialState
) {

    override fun processEvent(oldState: CallExecutionScreenState, event: CallExecutionScreenEvent) {
        when (event) {
            is CallingScreenEvent -> processCallingScreenEvent(oldState, event)
            is OngoingCallScreenEvent -> processOngoingCallScreenEvent(oldState, event)
        }
    }

    private fun processCallingScreenEvent(
        oldState: CallExecutionScreenState,
        event: CallingScreenEvent
    ) {
        when (event) {
            is CallingScreenEvent.CallParametersLoaded -> setState(
                CallExecutionScreenState.Calling.initial(event.callExecutionParams)
            )

            CallingScreenEvent.AcceptCall -> processAcceptCall(oldState)
            CallingScreenEvent.DeclineCall -> processDeclineCall(oldState)
        }
    }

    private fun processOngoingCallScreenEvent(
        oldState: CallExecutionScreenState,
        event: OngoingCallScreenEvent
    ) {
        when (event) {
            is OngoingCallScreenEvent.UpdateTimer -> processUpdateTimer(oldState, event.time)
            OngoingCallScreenEvent.EndCall -> processEndCall(oldState)
        }
    }

    private fun processAcceptCall(oldState: CallExecutionScreenState) {
        oldState as? CallExecutionScreenState.Calling
            ?: error("Call can be accepted only during calling state")

        setState(CallExecutionScreenState.OngoingCall.initial(oldState.callExecutionParams))
        startTimer()
    }

    private fun processDeclineCall(oldState: CallExecutionScreenState) {
        oldState as? CallExecutionScreenState.Calling
            ?: error("Call can be declined only during calling state")

        setState(oldState.copy(isDeclined = true))
        endCallAction()
    }

    private fun processUpdateTimer(oldState: CallExecutionScreenState, newTime: String) {
        oldState as? CallExecutionScreenState.OngoingCall
            ?: error("Timer can be updated only during ongoing call state")
        setState(oldState.copy(time = newTime))
    }

    private fun processEndCall(oldState: CallExecutionScreenState) {
        oldState as? CallExecutionScreenState.OngoingCall
            ?: error("Call can be cancelled only during ongoing call state")

        stopTimer()
        setState(oldState.copy(isCompleted = true))
        endCallAction()
    }
}
