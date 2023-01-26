package com.alenskaya.fakecalls.presentation.main.phonebook

import com.alenskaya.fakecalls.presentation.mvi.UiEvent
import com.alenskaya.fakecalls.presentation.phonebook.PhonebookContact

/**
 * All possible events of phonebook screen.
 */
sealed interface PhonebookScreenEvent : UiEvent {
    class ContactsLoaded(val contacts: List<PhonebookContact>) : PhonebookScreenEvent
    object PhonebookIsEmpty : PhonebookScreenEvent
    object Error : PhonebookScreenEvent
    class SelectContact(val contact: PhonebookContact) : PhonebookScreenEvent
    object NavigateBack : PhonebookScreenEvent
}
