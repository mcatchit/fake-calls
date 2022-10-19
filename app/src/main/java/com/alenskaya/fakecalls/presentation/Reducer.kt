package com.alenskaya.fakecalls.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Controls screen state
 * @param S - type of ui state
 * @param E - type of ui events
 */
abstract class Reducer<S : UiState, E : UiEvent>(initialVal: S) {

    private val mutableState: MutableStateFlow<S> = MutableStateFlow(initialVal)

    /**
     * Current state of the screen
     */
    val state: StateFlow<S>
        get() = mutableState

    /**
     * Receives [event] and changes screen state accordingly
     */
    fun sendEvent(event: E) {
        reduce(mutableState.value, event)
    }

    /**
     * Sets current state to [newState]
     */
    fun setState(newState: S) {
        mutableState.tryEmit(newState)
    }

    /**
     * Changes [oldState] according to the new [event]
     */
    abstract fun reduce(oldState: S, event: E)
}
