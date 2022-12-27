package com.alenskaya.fakecalls.presentation.main.calls

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.alenskaya.fakecalls.domain.calls.DeleteCallUseCase
import com.alenskaya.fakecalls.domain.calls.LoadSavedCallsUseCase
import com.alenskaya.fakecalls.presentation.CallsDataChangedListener
import com.alenskaya.fakecalls.presentation.CallsDataChangedNotifier
import com.alenskaya.fakecalls.presentation.execution.CallsScheduler
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.main.calls.model.CallType
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
import com.alenskaya.fakecalls.presentation.navigation.create.CreateRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel of CallsScreen
 * @property imageLoader - application image loader
 * @property loadSavedCallsUseCase - useCase for loading list of saved contacts
 */
@HiltViewModel
class CallsScreenViewModel @Inject constructor(
    val imageLoader: ImageLoader,
    val callsStrings: CallsStrings,
    private val router: ApplicationRouter,
    private val loadSavedCallsUseCase: LoadSavedCallsUseCase,
    private val deleteCallUseCase: DeleteCallUseCase,
    private val callsScheduler: CallsScheduler,
    private val callsDataChangedNotifier: CallsDataChangedNotifier
) : ViewModel() {

    val screenState: StateFlow<CallsScreenState>
        get() = reducer.state

    private val reducer = CallsScreenStateReducer(
        viewModelScope,
        CallsScreenState.initial(),
        ::loadCalls,
        ::repeatCall,
        ::editCall,
        ::deleteCall
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

    fun sendEvent(event: CallsScreenEvent) {
        reducer.sendEvent(event)
    }

    private fun refresh() {
        sendEvent(CallsScreenEvent.CallsLoading)
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

    private fun repeatCall(callId: Int) {
        router.navigate(CreateRoutes.RepeatCallRoute.createDestination(callId))
    }

    private fun editCall(callId: Int) {
        router.navigate(CreateRoutes.EditCallRoute.createDestination(callId))
    }

    private fun deleteCall(call: CallsScreenCallModel, callType: CallType) {
        viewModelScope.launch(Dispatchers.Main) {
            if (callType == CallType.SCHEDULED) {
                callsScheduler.cancelCall(call.toCallExecutionParams())
            }
            withContext(Dispatchers.IO) {
                deleteCallUseCase(call.id).collect {
                    callsDataChangedNotifier.callsDataChanged()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        callsDataChangedNotifier.removeListener(callsDataChangedListener)
    }

    private fun CallsScreenCallModel.toCallExecutionParams() = CallExecutionParams(
        callId = id,
        name = name,
        phone = phone,
        photoUrl = photoUrl,
        requestCode = requestCode
    )
}
