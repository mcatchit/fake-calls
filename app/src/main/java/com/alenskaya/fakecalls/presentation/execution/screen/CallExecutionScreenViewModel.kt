package com.alenskaya.fakecalls.presentation.execution.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.alenskaya.fakecalls.presentation.execution.ExecutionStrings
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams

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
 */
@HiltViewModel
class CallExecutionScreenViewModel @Inject constructor(
    val imageLoader: ImageLoader,
    val executionStrings: ExecutionStrings
) : ViewModel() {

    private var cancelCallAction by Delegates.notNull<CancelCallAction>()

    private var reducer = CallExecutionScreenStateReducer(
        viewModelScope,
        CallExecutionScreenState.initial(),
        ::cancelCall
    )

    val screenState: StateFlow<CallExecutionScreenState>
        get() = reducer.state

    /**
     * Must be call before usage.
     */
    fun init(cancelCallAction: CancelCallAction, callExecutionParams: CallExecutionParams) {
        this.cancelCallAction = cancelCallAction
        sendEvent(CallExecutionScreenEvent.CallParametersLoaded(callExecutionParams))
    }

    fun sendEvent(event: CallExecutionScreenEvent) {
        reducer.sendEvent(event)
    }

    private fun cancelCall() {
        viewModelScope.launch(Dispatchers.Main) {
            delay(CANCEL_CALL_DELAY)
            cancelCallAction()
        }
    }

    companion object {
        private const val CANCEL_CALL_DELAY = 2000L
    }
}
