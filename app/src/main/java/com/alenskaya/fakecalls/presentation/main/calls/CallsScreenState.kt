package com.alenskaya.fakecalls.presentation.main.calls

import com.alenskaya.fakecalls.presentation.mvi.UiState
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenCallModel

/**
 * Ui state of CallsScreen
 */
data class CallsScreenState(

    /**
     * List of scheduled calls.
     */
    val scheduledCalls: List<CallsScreenCallModel>,

    /**
     * List of completed calls.
     */
    val completedCalls: List<CallsScreenCallModel>,

    /**
     * Indicator of showing loading progress.
     */
    val isLoading: Boolean,

    /**
     * Displayed message.
     * If both lists are empty and [isLoading] = false must be not null.
     */
    val message: String?
) : UiState {

    companion object {
        fun initial() = CallsScreenState(
            scheduledCalls = listOf(),
            completedCalls = listOf(),
            isLoading = false,
            message = null
        )
    }

    /**
     * Returns true if both lists of calls are empty
     */
    fun areCallsEmpty(): Boolean {
        return scheduledCalls.isEmpty() && completedCalls.isEmpty()
    }
}
