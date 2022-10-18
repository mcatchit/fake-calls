package com.alenskaya.fakecalls.domain.contacts.usecase

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.UseCase
import com.alenskaya.fakecalls.domain.contacts.model.FakeContactsResponse
import com.alenskaya.fakecalls.domain.contacts.repository.FakeContactRepository

/**
 * Requests list of fake users
 */
class GetFakeContactsListUseCase(
    private val repository: FakeContactRepository
) : UseCase<BaseResponse<FakeContactsResponse>> {

    override suspend fun invoke(): BaseResponse<FakeContactsResponse> {
        return repository.getFakeUsers(FAKE_USERS_AMOUNT)
    }

    companion object {
        private const val FAKE_USERS_AMOUNT = 6
    }
}
