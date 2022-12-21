package com.alenskaya.fakecalls.presentation.main.calls

import com.alenskaya.fakecalls.presentation.main.calls.model.CallType
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

    /**
     * User clicked repeat button.
     * @property callId - call identifier.
     */
    class RepeatCall(val callId: Int) : CallsScreenEvent()

    /**
     * User clicked edit button.
     * @property callId - call identifier.
     */
    class EditCall(val callId: Int) : CallsScreenEvent()

    /**
     * User clicked delete button.
     * @property call - clicked call.
     * @property type - call type.
     */
    class DeleteCall(val call: CallsScreenCallModel, val type: CallType) : CallsScreenEvent()
}
