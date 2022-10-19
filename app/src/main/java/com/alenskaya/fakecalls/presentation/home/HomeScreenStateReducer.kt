package com.alenskaya.fakecalls.presentation.home

import com.alenskaya.fakecalls.presentation.Reducer

/**
 * Manages HomeScreen state
 */
class HomeScreenStateReducer(
    initialState: HomeScreenState
) : Reducer<HomeScreenState, HomeScreenEvent>(initialState) {

    override fun reduce(oldState: HomeScreenState, event: HomeScreenEvent) {
        val newState = when (event) {
            is HomeScreenEvent.ContactsLoading -> oldState.copy(isLoading = true)
            is HomeScreenEvent.ContactsLoaded -> oldState.copy(
                isLoading = false,
                contacts = event.contacts
            )
            is HomeScreenEvent.ContactsNotLoaded -> oldState.copy(
                isLoading = false,
                message = event.message
            )
            is HomeScreenEvent.HideNotLoadedMessage -> oldState.copy(
                message = null
            )
        }

        setState(newState)
    }
}
