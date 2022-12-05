package com.alenskaya.fakecalls.presentation.main.home

import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContact
import com.alenskaya.fakecalls.presentation.mvi.UiEvent

/**
 * Possible events on HomeScreen
 */
sealed class HomeScreenEvent : UiEvent {
    object ContactsLoading : HomeScreenEvent()
    class ContactsLoaded(val contacts: List<SavedFakeContact>) : HomeScreenEvent()
    class ContactsNotLoaded(val message: String) : HomeScreenEvent()
    class ContactHintVisibilityChanged(val contactId: Int, val isHinted: Boolean) :
        HomeScreenEvent()
}
