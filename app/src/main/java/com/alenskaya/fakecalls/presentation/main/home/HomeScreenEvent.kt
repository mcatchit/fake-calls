package com.alenskaya.fakecalls.presentation.main.home

import com.alenskaya.fakecalls.domain.contacts.model.SavedContact
import com.alenskaya.fakecalls.presentation.mvi.UiEvent

/**
 * Possible events on HomeScreen
 */
sealed class HomeScreenEvent : UiEvent {

    /**
     * Contacts suggestions are loading.
     */
    object ContactsLoading : HomeScreenEvent()

    /**
     * Contacts suggestions are loaded successfully.
     * @property contacts - contacts suggestions.
     */
    class ContactsLoaded(val contacts: List<SavedContact>) : HomeScreenEvent()

    /**
     * Contacts suggestions were not loaded.
     * @property message - message to display.
     */
    class ContactsNotLoaded(val message: String) : HomeScreenEvent()

    /**
     * Contact hint visibility was changed.
     * @property contactId - contact id.
     * @property isHinted - is contact hinted now.
     */
    class ContactHintVisibilityChanged(val contactId: Int, val isHinted: Boolean) :
        HomeScreenEvent()

    /**
     * User clicked to create a call from suggested contact.
     * @property fakeContactId - contact id.
     */
    class CreateCallFromSuggested(val fakeContactId: Int) : HomeScreenEvent()

    /**
     * User clicked to create a custom call.
     */
    object CreateCustomCall : HomeScreenEvent()

    object SelectFromPhonebook : HomeScreenEvent()
}
