package com.alenskaya.fakecalls.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenskaya.fakecalls.domain.contacts.usecase.GetFakeContactsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * ViewModel of HomeScreen
 * @property getContactsUseCase - useCase for loading list of fake contacts
 */
class HomeScreenViewModel(
    private val getContactsUseCase: GetFakeContactsListUseCase
) : ViewModel() {

    private val reducer = HomeScreenStateReducer(HomeScreenState.initial())

    val screenState: StateFlow<HomeScreenState>
        get() = reducer.state

    init {
        loadFakeContacts()
    }

    fun contactHintVisibilityChanged(contactId: Int, isHinted: Boolean) {
        sendEvent(HomeScreenEvent.ContactHintVisibilityChanged(contactId, isHinted))
    }

    private fun loadFakeContacts() {
        sendEvent(HomeScreenEvent.ContactsLoading)

        viewModelScope.launch(Dispatchers.IO) {
            getContactsUseCase().map { fakeContactsResponse ->
                FakeContactsResponseToHomeScreenEventConverter.convert(fakeContactsResponse)
            }.collect { homeScreenEvent ->
                sendEvent(homeScreenEvent)
            }
        }
    }

    private fun sendEvent(event: HomeScreenEvent) {
        reducer.sendEvent(event)
    }
}
