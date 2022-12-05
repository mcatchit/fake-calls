package com.alenskaya.fakecalls.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.alenskaya.fakecalls.domain.contacts.GetFakeContactsListUseCase
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
import com.alenskaya.fakecalls.presentation.navigation.create.CreateRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel of HomeScreen
 * @property imageLoader - application image loader
 * @property getContactsUseCase - useCase for loading list of fake contacts
 */
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val imageLoader: ImageLoader,
    private val getContactsUseCase: GetFakeContactsListUseCase,
    private val applicationRouter: ApplicationRouter
) : ViewModel() {

    private val reducer = HomeScreenStateReducer(viewModelScope, HomeScreenState.initial())

    val screenState: StateFlow<HomeScreenState>
        get() = reducer.state

    val oneTimeEffect: SharedFlow<HomeScreenOneTimeUiEffect>
        get() = reducer.oneTimeEffect

    init {
        loadFakeContacts()
    }

    fun contactHintVisibilityChanged(contactId: Int, isHinted: Boolean) {
        sendEvent(HomeScreenEvent.ContactHintVisibilityChanged(contactId, isHinted))
    }

    fun createFakeCall(contactId: Int) {
        applicationRouter.navigate(
            CreateRoutes.CreateCallFromFakeContactRoute.createDestination(
                contactId
            )
        )
    }

    fun createCustomCall() {
        applicationRouter.navigate(CreateRoutes.CreateCallFromCustomContactRoute.createDestination())
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
