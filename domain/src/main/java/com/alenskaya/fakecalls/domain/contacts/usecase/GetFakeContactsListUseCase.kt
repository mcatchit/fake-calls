package com.alenskaya.fakecalls.domain.contacts.usecase

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.RemoteRequestErrorCause
import com.alenskaya.fakecalls.domain.contacts.model.FakeContactsResponse
import com.alenskaya.fakecalls.domain.contacts.repository.FakeContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Requests list of fake users
 */
class GetFakeContactsListUseCase @Inject constructor(
    private val repository: FakeContactRepository
) {

    suspend operator fun invoke(): Flow<BaseResponse<FakeContactsResponse, RemoteRequestErrorCause>> =
        flow {
            emit(repository.getFakeUsers(FAKE_USERS_AMOUNT))
        }

    companion object {
        private const val FAKE_USERS_AMOUNT = 5
    }
}
