package com.alenskaya.fakecalls.presentation.main.phonebook

import com.alenskaya.fakecalls.presentation.mvi.OneTimeUiEffect
import com.alenskaya.fakecalls.presentation.mvi.Reducer
import com.alenskaya.fakecalls.presentation.phonebook.PhonebookContact
import kotlinx.coroutines.CoroutineScope

/**
 * Manages phonebook screen state.
 */
class PhonebookScreenStateReducer(
    coroutineScope: CoroutineScope,
    initialStateValue: PhonebookScreenState,
    private val strings: PhonebookStrings,
    private val navigateBackAction: () -> Unit,
    private val saveContactAction: (PhonebookContact) -> Unit
) : Reducer<PhonebookScreenState, PhonebookScreenEvent, OneTimeUiEffect>(
    coroutineScope,
    initialStateValue
) {

    override fun processEvent(oldState: PhonebookScreenState, event: PhonebookScreenEvent) {
        when (event) {
            is PhonebookScreenEvent.ContactsLoaded -> setState(
                oldState.copy(
                    contacts = event.contacts,
                    isLoading = false
                )
            )
            is PhonebookScreenEvent.Error -> setState(
                oldState.copy(
                    isLoading = false,
                    errorMessage = strings.errorMessage()
                )
            )
            is PhonebookScreenEvent.PhonebookIsEmpty -> setState(
                oldState.copy(
                    isLoading = false,
                    errorMessage = strings.empty()
                )
            )
            is PhonebookScreenEvent.NavigateBack -> navigateBackAction()
            is PhonebookScreenEvent.SelectContact -> saveContactAction(event.contact)
        }
    }
}
