package com.alenskaya.fakecalls.domain.contacts

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.ErrorCause
import com.alenskaya.fakecalls.domain.RemoteRequestErrorCause
import com.alenskaya.fakecalls.domain.contacts.model.RemoteFakeContactsResponse
import com.alenskaya.fakecalls.domain.contacts.model.SavedContactsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

/**
 * Requests list of suggested users.
 */
class GetSuggestedContactsListUseCase @Inject constructor(
    private val remoteRepository: FakeContactRemoteRepository,
    private val localRepository: ContactsLocalRepository
) {

    operator fun invoke(): Flow<BaseResponse<SavedContactsResponse, out ErrorCause>> {
        return flow {
            emit(remoteRepository.getFakeUsers(CONTACTS_QUANTITY))
        }.map { remoteResponse ->
            remoteResponse.toSavedContactsResponse()
        }.transform { response ->
            if (response is BaseResponse.Error) {
                emit(BaseResponse.Error(DatabaseError))
            } else {
                localRepository.getSavedContacts(CONTACTS_QUANTITY).collect {
                    emit(it)
                }
            }
        }
    }

    private suspend fun BaseResponse<RemoteFakeContactsResponse, RemoteRequestErrorCause>.toSavedContactsResponse() =
        when (this) {
            is BaseResponse.Success -> localRepository
                .saveContacts(payload.contacts)

            is BaseResponse.Error -> BaseResponse.Error(cause)
        }

    companion object {
        private const val CONTACTS_QUANTITY = 5
    }
}
