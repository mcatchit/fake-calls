package com.alenskaya.fakecalls.domain.contacts

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.ErrorCause
import com.alenskaya.fakecalls.domain.RemoteRequestErrorCause
import com.alenskaya.fakecalls.domain.contacts.model.RemoteFakeContactsResponse
import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContactsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

/**
 * Requests list of fake users
 */
class GetFakeContactsListUseCase @Inject constructor(
    private val remoteRepository: FakeContactRemoteRepository,
    private val localRepository: FakeContactLocalRepository
) {

    operator fun invoke(): Flow<BaseResponse<SavedFakeContactsResponse, out ErrorCause>> {
        return flow {
            emit(remoteRepository.getFakeUsers(FAKE_USERS_AMOUNT))
        }.map { remoteResponse ->
            remoteResponse.toSavedContactsResponse()
        }.transform { response ->
            if (response is BaseResponse.Error) {
                emit(BaseResponse.Error(DatabaseError))
            } else {
                localRepository.getFakeContacts().collect {
                    emit(it)
                }
            }
        }
    }

    private suspend fun BaseResponse<RemoteFakeContactsResponse, RemoteRequestErrorCause>.toSavedContactsResponse() =
        when (this) {
            is BaseResponse.Success -> localRepository
                .saveFakeContacts(payload.contacts)

            is BaseResponse.Error -> BaseResponse.Error(cause)
        }

    companion object {
        private const val FAKE_USERS_AMOUNT = 5
    }
}
