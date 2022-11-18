package com.alenskaya.fakecalls.presentation.feature.home

import com.alenskaya.fakecalls.domain.contacts.model.FakeContact
import com.alenskaya.fakecalls.presentation.UiEvent

/**
 * Possible events on HomeScreen
 */
sealed class HomeScreenEvent : UiEvent {
    object ContactsLoading : HomeScreenEvent()
    class ContactsLoaded(val contacts: List<FakeContact>) : HomeScreenEvent()
    class ContactsNotLoaded(val message: String) : HomeScreenEvent()
    class ContactHintVisibilityChanged(val contactId: Int, val isHinted: Boolean) :
        HomeScreenEvent()
}
