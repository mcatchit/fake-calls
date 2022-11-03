package com.alenskaya.fakecalls.presentation.calls

import com.alenskaya.fakecalls.presentation.UiEvent
import com.alenskaya.fakecalls.presentation.calls.model.CallsScreenCallModel

/**
 * Possible events on CallsScreen
 */
sealed class CallsScreenEvent : UiEvent {
    object CallsLoading : CallsScreenEvent()

    class CallsLoaded(
        val scheduledCalls: List<CallsScreenCallModel>,
        val completedCalls: List<CallsScreenCallModel>
    ) : CallsScreenEvent()

    object CallsLoadedEmpty : CallsScreenEvent()
    object CallsNotLoaded : CallsScreenEvent()
}
