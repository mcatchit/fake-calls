package com.alenskaya.fakecalls.presentation.main.home

import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContact
import com.alenskaya.fakecalls.presentation.mvi.Reducer
import com.alenskaya.fakecalls.presentation.main.home.model.HomeScreenFakeContactModel
import kotlinx.coroutines.CoroutineScope

/**
 * Manages HomeScreen state
 */
class HomeScreenStateReducer(
    viewModelScope: CoroutineScope,
    initialState: HomeScreenState
) : Reducer<HomeScreenState, HomeScreenEvent, HomeScreenOneTimeUiEffect>(
    viewModelScope,
    initialState
) {

    override fun processEvent(oldState: HomeScreenState, event: HomeScreenEvent) {
        setState(oldState.reduce(event))

        if (event is HomeScreenEvent.ContactsNotLoaded) {
            sendOneTimeEffect(HomeScreenOneTimeUiEffect(event.message))
        }
    }

    private fun HomeScreenState.reduce(event: HomeScreenEvent): HomeScreenState {
        return when (event) {
            is HomeScreenEvent.ContactsLoading -> copy(isLoading = true)
            is HomeScreenEvent.ContactsLoaded -> copy(
                isLoading = false,
                contacts = event.contacts.toScreenModel()
            )
            is HomeScreenEvent.ContactsNotLoaded -> copy(
                isLoading = false
            )
            is HomeScreenEvent.ContactHintVisibilityChanged -> copy(
                contacts = contacts.changeContactsHintVisibility(
                    event.contactId,
                    event.isHinted
                )
            )
        }
    }

    private fun List<SavedFakeContact>.toScreenModel() = map { fakeContact ->
        HomeScreenFakeContactModel(
            id = fakeContact.id,
            name = fakeContact.name,
            phone = fakeContact.phone,
            photoUrl = fakeContact.photoUrl,
            isHintVisible = false
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
