package com.alenskaya.fakecalls.presentation.home

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.RemoteRequestErrorCause
import com.alenskaya.fakecalls.domain.contacts.model.FakeContactsResponse
import com.alenskaya.fakecalls.utils.Converter

//TODO move messages to strings
object FakeContactsResponseToHomeScreenEventConverter :
    Converter<BaseResponse<FakeContactsResponse>, HomeScreenEvent> {

    override fun convert(input: BaseResponse<FakeContactsResponse>): HomeScreenEvent {
        return when (input) {
            is BaseResponse.Success -> HomeScreenEvent.ContactsLoaded(input.payload.contacts)
            is BaseResponse.Error -> HomeScreenEvent.ContactsNotLoaded(getErrorMessage(input.cause))
        }
    }

    private fun getErrorMessage(cause: RemoteRequestErrorCause): String {
        return when (cause) {
            RemoteRequestErrorCause.NoInternetConnection -> "No internet connection. Can not load suggestions"
            else -> "Failed to load contacts suggestions. You still may select a contact from phonebook or create a custom one."
        }
    }
}
