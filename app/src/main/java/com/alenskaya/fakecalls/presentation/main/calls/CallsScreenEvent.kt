package com.alenskaya.fakecalls.presentation.main.calls

import com.alenskaya.fakecalls.presentation.mvi.UiEvent
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenCallModel

/**
 * Possible events on CallsScreen
 */
sealed class CallsScreenEvent : UiEvent {
    object LoadCalls : CallsScreenEvent()

    class CallsLoaded(
        val scheduledCalls: List<CallsScreenCallModel>,
        val completedCalls: List<CallsScreenCallModel>
    ) : CallsScreenEvent()

    object CallsLoadedEmpty : CallsScreenEvent()
    object CallsNotLoaded : CallsScreenEvent()
}
