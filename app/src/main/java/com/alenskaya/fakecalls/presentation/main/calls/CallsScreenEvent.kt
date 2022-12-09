package com.alenskaya.fakecalls.presentation.main.calls

import com.alenskaya.fakecalls.presentation.mvi.UiEvent
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenCallModel

/**
 * Possible events on CallsScreen.
 */
sealed class CallsScreenEvent : UiEvent {

    /**
     * Calls are loading.
     */
    object CallsLoading : CallsScreenEvent()

    /**
     * Calls have been loaded successfully.
     * @property scheduledCalls - list of scheduled calls.
     * @property completedCalls - list of completed calls.
     */
    class CallsLoaded(
        val scheduledCalls: List<CallsScreenCallModel>,
        val completedCalls: List<CallsScreenCallModel>
    ) : CallsScreenEvent()

    /**
     * Calls have been loaded successfully, but both lists are empty.
     */
    object CallsLoadedEmpty : CallsScreenEvent()

    /**
     * An error occurred during loading calls.
     */
    object CallsNotLoaded : CallsScreenEvent()
}
