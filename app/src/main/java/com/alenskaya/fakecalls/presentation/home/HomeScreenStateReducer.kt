package com.alenskaya.fakecalls.presentation.home

import com.alenskaya.fakecalls.domain.contacts.model.FakeContact
import com.alenskaya.fakecalls.presentation.Reducer
import com.alenskaya.fakecalls.presentation.home.model.HomeScreenFakeContactModel

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
                contacts = event.contacts.toScreenModel()
            )
            is HomeScreenEvent.ContactsNotLoaded -> oldState.copy(
                isLoading = false,
                message = event.message
            )
            is HomeScreenEvent.HideNotLoadedMessage -> oldState.copy(
                message = null
            )
            is HomeScreenEvent.ContactHintVisibilityChanged -> oldState.copy(
                contacts = oldState.contacts.changeContactsHintVisibility(
                    event.contactId,
                    event.isHinted
                )
            )
        }

        setState(newState)
    }

    private fun List<FakeContact>.toScreenModel() = mapIndexed { index, fakeContact ->
        HomeScreenFakeContactModel(
            id = index,
            contact = fakeContact
        )
    }

    private fun List<HomeScreenFakeContactModel>.changeContactsHintVisibility(
        contactId: Int,
        isHintVisible: Boolean
    ): List<HomeScreenFakeContactModel> {
        return map { contact ->
            when (contact.id == contactId) {
                true -> contact.copy(isHintVisible = isHintVisible)
                false -> if (isHintVisible) contact.hideHint() else contact
            }
        }
    }

    private fun HomeScreenFakeContactModel.hideHint() = copy(isHintVisible = false)
}
