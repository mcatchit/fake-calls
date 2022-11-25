package com.alenskaya.fakecalls.presentation.features.calls

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.alenskaya.fakecalls.domain.calls.LoadSavedCallsUseCase
import com.alenskaya.fakecalls.presentation.CallsDataChangedListener
import com.alenskaya.fakecalls.presentation.CallsDataChangedNotifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel of CallsScreen
 * @property imageLoader - application image loader
 * @property loadSavedCallsUseCase - useCase for loading list of saved contacts
 */
@HiltViewModel
class CallsScreenViewModel @Inject constructor(
    val imageLoader: ImageLoader,
    private val loadSavedCallsUseCase: LoadSavedCallsUseCase,
    private val callsDataChangedNotifier: CallsDataChangedNotifier
) : ViewModel() {

    val screenState: StateFlow<CallsScreenState>
        get() = reducer.state

    private val reducer = CallsScreenStateReducer(
        viewModelScope,
        CallsScreenState.initial(),
        ::loadCalls
    )

    private val callsDataChangedListener = object : CallsDataChangedListener {
        override fun callsDataChanged() {
            refresh()
        }
    }

    init {
        callsDataChangedNotifier.addListener(callsDataChangedListener)
        refresh()
    }

    private fun refresh() {
        sendEvent(CallsScreenEvent.LoadCalls)
    }

    private fun loadCalls() {
        viewModelScope.launch(Dispatchers.IO) {
            loadSavedCallsUseCase().map { savedCallsResponse ->
                SavedCallsRequestResultToCallsScreenEventConverter.convert(savedCallsResponse)
            }.collect { callsScreenEvent ->
                sendEvent(callsScreenEvent)
            }
        }
    }

    private fun sendEvent(event: CallsScreenEvent) {
        reducer.sendEvent(event)
    }

    override fun onCleared() {
        super.onCleared()
        callsDataChangedNotifier.removeListener(callsDataChangedListener)
    }
}
