package com.alenskaya.fakecalls.presentation.main.home

import com.alenskaya.fakecalls.domain.contacts.model.SavedContact
import com.alenskaya.fakecalls.presentation.mvi.Reducer
import com.alenskaya.fakecalls.presentation.main.home.model.HomeScreenFakeContactModel
import kotlinx.coroutines.CoroutineScope

/**
 * Manages HomeScreen state.
 */
class HomeScreenStateReducer(
    viewModelScope: CoroutineScope,
    initialState: HomeScreenState,
    private val navigateToCreateCallScreenAction: (Int?) -> Unit,
    private val navigateToPhonebookAction: () -> Unit
) : Reducer<HomeScreenState, HomeScreenEvent, HomeScreenOneTimeUiEffect>(
    viewModelScope,
    initialState
) {

    override fun processEvent(oldState: HomeScreenState, event: HomeScreenEvent) {
        return when (event) {
            is HomeScreenEvent.ContactsLoading -> setState(oldState.copy(isLoading = true))

            is HomeScreenEvent.ContactsLoaded -> processContactsLoaded(oldState, event.contacts)

            is HomeScreenEvent.ContactsNotLoaded -> processContactsNotLoaded(
                oldState,
                event.message
            )

            is HomeScreenEvent.ContactHintVisibilityChanged -> processHintVisibilityChanged(
                oldState,
                event
            )

            is HomeScreenEvent.CreateCallFromSuggested -> navigateToCreateCallScreenAction(event.fakeContactId)
            is HomeScreenEvent.CreateCustomCall -> navigateToCreateCallScreenAction(null)
            is HomeScreenEvent.SelectFromPhonebook -> navigateToPhonebookAction()
            is HomeScreenEvent.PermissionNotGranted -> showToast(event.message)
        }
    }

    private fun processContactsLoaded(oldState: HomeScreenState, contacts: List<SavedContact>) {
        setState(
            oldState.copy(
                isLoading = false,
                contacts = contacts.toScreenModel()
            )
        )
    }

    private fun processContactsNotLoaded(oldState: HomeScreenState, message: String) {
        setState(oldState.copy(isLoading = false))
        showToast(message)
    }

    private fun processHintVisibilityChanged(
        oldState: HomeScreenState,
        event: HomeScreenEvent.ContactHintVisibilityChanged
    ) {
        setState(
            oldState.copy(
                contacts = oldState.contacts.changeContactsHintVisibility(
                    event.contactId,
                    event.isHinted
                )
            )
        )
    }

    private fun showToast(message: String){
        sendOneTimeEffect(HomeScreenOneTimeUiEffect(message))
    }

    private fun List<SavedContact>.toScreenModel() = map { fakeContact ->
        HomeScreenFakeContactModel(
            id = fakeContact.id,
            name = fakeContact.name,
            phone = fakeContact.phone,
            photoUrl = fakeContact.photoUrl,
            country = fakeContact.country,
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
