package com.alenskaya.fakecalls.presentation.main.home

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.alenskaya.fakecalls.domain.contacts.GetSuggestedContactsListUseCase
import com.alenskaya.fakecalls.presentation.PermissionManager
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
import com.alenskaya.fakecalls.presentation.navigation.NavigationDestination
import com.alenskaya.fakecalls.presentation.navigation.PhonebookScreenNavigationDestination
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
    val homeStrings: HomeStrings,
    private val getContactsUseCase: GetSuggestedContactsListUseCase,
    private val applicationRouter: ApplicationRouter,
    private val permissionManager: PermissionManager
) : ViewModel() {

    private val reducer = HomeScreenStateReducer(
        viewModelScope,
        HomeScreenState.initial(),
        ::navigateToCreateCallScreen,
        ::navigateToPhonebook
    )

    val screenState: StateFlow<HomeScreenState>
        get() = reducer.state

    val oneTimeEffect: SharedFlow<HomeScreenOneTimeUiEffect>
        get() = reducer.oneTimeEffect

    init {
        loadFakeContacts()
    }

    fun sendEvent(event: HomeScreenEvent) {
        reducer.sendEvent(event)
    }

    private fun loadFakeContacts() {
        sendEvent(HomeScreenEvent.ContactsLoading)

        viewModelScope.launch(Dispatchers.IO) {
            getContactsUseCase().map { fakeContactsResponse ->
                FakeContactsResponseToHomeScreenEventConverter(homeStrings).convert(
                    fakeContactsResponse
                )
            }.collect { homeScreenEvent ->
                sendEvent(homeScreenEvent)
            }
        }
    }

    private fun navigateToCreateCallScreen(contactId: Int? = null) {
        applicationRouter.navigate(getCreateCallScreenDestination(contactId))
    }

    private fun navigateToPhonebook() {
        permissionManager.requestPermission(
            Manifest.permission.READ_CONTACTS,
        ) { granted ->
            processReadPhonebookResult(granted)
        }
    }

    private fun processReadPhonebookResult(isPermissionGranted: Boolean) =
        if (isPermissionGranted) {
            applicationRouter.navigate(PhonebookScreenNavigationDestination)
        } else {
            sendEvent(HomeScreenEvent.PermissionNotGranted(homeStrings.permissionReadContactsNotGranted()))
        }

    private fun getCreateCallScreenDestination(contactId: Int? = null): NavigationDestination {
        return if (contactId == null) {
            CreateRoutes.CreateCallFromCustomContactRoute.createDestination()
        } else {
            CreateRoutes.CreateCallFromContactRoute.createDestination(contactId)
        }
    }
}
