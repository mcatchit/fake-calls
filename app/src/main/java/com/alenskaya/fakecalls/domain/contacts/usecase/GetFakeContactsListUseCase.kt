package com.alenskaya.fakecalls.domain.contacts.usecase

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.UseCase
import com.alenskaya.fakecalls.domain.contacts.model.FakeContactsResponse
import com.alenskaya.fakecalls.domain.contacts.repository.FakeContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Requests list of fake users
 */
class GetFakeContactsListUseCase(
    private val repository: FakeContactRepository
) : UseCase<Flow<BaseResponse<FakeContactsResponse>>> {

    override suspend fun invoke(): Flow<BaseResponse<FakeContactsResponse>> = flow {
        emit(repository.getFakeUsers(FAKE_USERS_AMOUNT))
    }

    companion object {
        private const val FAKE_USERS_AMOUNT = 6
    }
}
