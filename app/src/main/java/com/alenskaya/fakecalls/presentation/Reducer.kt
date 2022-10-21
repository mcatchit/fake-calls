package com.alenskaya.fakecalls.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Controls screen state
 * @param S - type of ui state
 * @param E - type of ui events
 */
abstract class Reducer<S : UiState, E : UiEvent, O : OneTimeUiEffect>(
    private val coroutineScope: CoroutineScope,
    initialStateValue: S
) {

    private val mutableState: MutableStateFlow<S> = MutableStateFlow(initialStateValue)

    private val mutableSharedOneTimeEffect: MutableSharedFlow<O> = MutableSharedFlow()

    /**
     * Current state of the screen
     */
    val state: StateFlow<S>
        get() = mutableState

    /**
     * One time effect
     */
    val oneTimeEffect: SharedFlow<O>
        get() = mutableSharedOneTimeEffect

    /**
     * Receives [event] and changes screen state accordingly
     */
    fun sendEvent(event: E) {
        processEvent(mutableState.value, event)
    }

    /**
     * Notifies [oneTimeEffect] listeners about one-time [effect]
     */
    fun sendOneTimeEffect(effect: O) {
        coroutineScope.launch {
            mutableSharedOneTimeEffect.emit(effect)
        }
    }

    /**
     * Sets current state to [newState]
     */
    fun setState(newState: S) {
        coroutineScope.launch {
            mutableState.emit(newState)
        }
    }

    /**
     * Changes [oldState] according to the new [event]
     */
    abstract fun processEvent(oldState: S, event: E)
}
