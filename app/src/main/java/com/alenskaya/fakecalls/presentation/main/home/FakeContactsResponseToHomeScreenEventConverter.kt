package com.alenskaya.fakecalls.presentation.main.home

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.ErrorCause
import com.alenskaya.fakecalls.domain.RemoteRequestErrorCause
import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContactsResponse
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts fake contacts remote response to HomeScreen event.
 * TODO move messages to strings
 */
object FakeContactsResponseToHomeScreenEventConverter :
    Converter<BaseResponse<SavedFakeContactsResponse, out ErrorCause>, HomeScreenEvent> {

    override fun convert(input: BaseResponse<SavedFakeContactsResponse, out ErrorCause>): HomeScreenEvent {
        return when (input) {
            is BaseResponse.Success -> HomeScreenEvent.ContactsLoaded(input.payload.contacts)
            is BaseResponse.Error -> HomeScreenEvent.ContactsNotLoaded(getErrorMessage(input.cause))
        }
    }

    private fun getErrorMessage(cause: ErrorCause): String {
        return when (cause) {
            RemoteRequestErrorCause.NoInternetConnection -> "No internet connection. Can not load suggestions"
            else -> "Failed to load contacts suggestions. You still may select a contact from phonebook or create a custom one."
        }
    }
}
