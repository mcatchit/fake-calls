package com.alenskaya.fakecalls.presentation.execution.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.alenskaya.fakecalls.presentation.execution.ExecutionStrings
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.execution.screen.ongoing.OngoingCallSecondsTimer
import com.alenskaya.fakecalls.presentation.execution.screen.ongoing.OngoingCallSecondsToStringConverter

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

typealias CancelCallAction = () -> Unit

/**
 * View model of call execution screen.
 * @param imageLoader - application image loader.
 * @param executionStrings - string resources for call execution.
 */
@HiltViewModel
class CallExecutionScreenViewModel @Inject constructor(
    val imageLoader: ImageLoader,
    val executionStrings: ExecutionStrings
) : ViewModel() {

    private var endCallAction by Delegates.notNull<CancelCallAction>()

    private var timer = OngoingCallSecondsTimer(viewModelScope, ::updateTime, ::timeLimitExceeded)

    private var reducer = CallExecutionScreenStateReducer(
        viewModelScope,
        CallExecutionScreenState.Default,
        ::cancelCall,
        ::startTimer,
        ::stopTimer
    )

    val screenState: StateFlow<CallExecutionScreenState>
        get() = reducer.state

    /**
     * Must be call before usage.
     */
    fun init(endCallAction: CancelCallAction, callExecutionParams: CallExecutionParams) {
        this.endCallAction = endCallAction
        sendEvent(CallingScreenEvent.CallParametersLoaded(callExecutionParams))
    }

    /**
     * Sends event.
     */
    fun sendEvent(event: CallExecutionScreenEvent) {
        reducer.sendEvent(event)
    }

    private fun updateTime(seconds: Int) {
        sendEvent(
            OngoingCallScreenEvent.UpdateTimer(
                OngoingCallSecondsToStringConverter.convert(seconds)
            )
        )
    }

    private fun timeLimitExceeded() {
        sendEvent(OngoingCallScreenEvent.EndCall)
    }

    private fun cancelCall() {
        viewModelScope.launch(Dispatchers.Main) {
            delay(CANCEL_CALL_DELAY)
            endCallAction()
        }
    }

    private fun startTimer() {
        timer.start()
    }

    private fun stopTimer() {
        timer.stop()
    }

    companion object {
        private const val CANCEL_CALL_DELAY = 2000L
    }
}
