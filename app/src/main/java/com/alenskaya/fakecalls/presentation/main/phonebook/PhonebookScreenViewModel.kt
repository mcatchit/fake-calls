package com.alenskaya.fakecalls.presentation.main.phonebook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.contacts.SaveSingleContactUseCase
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
import com.alenskaya.fakecalls.presentation.navigation.NavigateBack
import com.alenskaya.fakecalls.presentation.navigation.create.CreateRoutes
import com.alenskaya.fakecalls.presentation.phonebook.PhonebookContact
import com.alenskaya.fakecalls.presentation.phonebook.PhonebookContactsProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel for phonebook screen.
 * @property phonebookStrings - string resources.
 * @property imageLoader - application image loader.
 * @property phonebookContactsProvider - gives access to device phonebook.
 * @property applicationRouter - gives access to application navigation.
 */
@HiltViewModel
class PhonebookScreenViewModel @Inject constructor(
    val phonebookStrings: PhonebookStrings,
    val imageLoader: ImageLoader,
    private val phonebookContactsProvider: PhonebookContactsProvider,
    private val applicationRouter: ApplicationRouter,
    private val saveSingleContactUseCase: SaveSingleContactUseCase
) : ViewModel() {

    private val reducer =
        PhonebookScreenStateReducer(
            viewModelScope,
            PhonebookScreenState.initial(),
            phonebookStrings,
            ::navigateBack,
            ::createCall
        )

    val screenState: StateFlow<PhonebookScreenState>
        get() = reducer.state

    init {
        loadContacts()
    }

    fun sendEvent(event: PhonebookScreenEvent) {
        reducer.sendEvent(event)
    }

    private fun navigateBack() {
        applicationRouter.navigate(NavigateBack)
    }

    private fun loadContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val contacts = phonebookContactsProvider.getAllContacts()
                if (contacts.isNotEmpty()) {
                    sendEvent(PhonebookScreenEvent.ContactsLoaded(contacts))
                } else {
                    sendEvent(PhonebookScreenEvent.PhonebookIsEmpty)
                }
            } catch (e: Exception) {
                sendEvent(PhonebookScreenEvent.Error)
            }
        }
    }

    private fun createCall(contact: PhonebookContact) {
        viewModelScope.launch(Dispatchers.IO) {
            saveSingleContactUseCase(
                PhonebookContactToContactToSaveConverter.convert(contact)
            ).collect { response ->
                if (response is BaseResponse.Success) {
                    withContext(Dispatchers.Main) {
                        navigateToCreateScreen(response.payload)
                    }
                }
            }
        }
    }

    private fun navigateToCreateScreen(id: Int) {
        applicationRouter.navigate(CreateRoutes.CreateCallFromContactRoute.createDestination(id))
    }
}
