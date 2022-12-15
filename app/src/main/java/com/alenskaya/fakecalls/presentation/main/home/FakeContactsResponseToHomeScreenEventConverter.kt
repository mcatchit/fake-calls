package com.alenskaya.fakecalls.presentation.main.home

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.ErrorCause
import com.alenskaya.fakecalls.domain.RemoteRequestErrorCause
import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContactsResponse
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts fake contacts remote response to HomeScreen event.
 */
class FakeContactsResponseToHomeScreenEventConverter(
    private val homeStrings: HomeStrings
) :
    Converter<BaseResponse<SavedFakeContactsResponse, out ErrorCause>, HomeScreenEvent> {

    override fun convert(input: BaseResponse<SavedFakeContactsResponse, out ErrorCause>): HomeScreenEvent {
        return when (input) {
            is BaseResponse.Success -> HomeScreenEvent.ContactsLoaded(input.payload.contacts)
            is BaseResponse.Error -> HomeScreenEvent.ContactsNotLoaded(getErrorMessage(input.cause))
        }
    }

    private fun getErrorMessage(cause: ErrorCause): String {
        return when (cause) {
            RemoteRequestErrorCause.NoInternetConnection -> homeStrings.noInternetConnectionMessage()
            else -> homeStrings.failedToLoadContactsMessage()
        }
    }
}
