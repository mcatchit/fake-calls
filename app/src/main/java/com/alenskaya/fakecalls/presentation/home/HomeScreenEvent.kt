package com.alenskaya.fakecalls.presentation.home

import com.alenskaya.fakecalls.domain.contacts.model.FakeContact
import com.alenskaya.fakecalls.presentation.UiEvent

/**
 * Possible events on HomeScreen
 */
sealed class HomeScreenEvent: UiEvent {
    class ContactsLoaded(val contacts: List<FakeContact>): HomeScreenEvent()
    class ContactsNotLoaded(val message: String): HomeScreenEvent()
    object ContactsLoading: HomeScreenEvent()
    object HideNotLoadedMessage: HomeScreenEvent()
}
