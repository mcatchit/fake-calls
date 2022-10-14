package com.alenskaya.fakecalls.data.remote.contacts

import com.alenskaya.fakecalls.data.remote.catchExceptions
import com.alenskaya.fakecalls.data.remote.toSuccessResponse
import com.alenskaya.fakecalls.data.remote.contacts.api.FakeContactApi
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.contacts.FakeContactRepository
import com.alenskaya.fakecalls.domain.contacts.model.FakeContactsResponse

/**
 * Implementation of fake contacts repository which refers to [FakeContactApi].
 */
internal class FakeContactRepositoryImpl(
    private val fakeContactApi: FakeContactApi
) : FakeContactRepository {

    override suspend fun getFakeUsers(amount: Int): BaseResponse<FakeContactsResponse> {
        return catchExceptions {
            fakeContactApi.getFakeContacts(amount)
                .toSuccessResponse(FakeContactResponseDtoToDomainResponseConverter())
        }
    }
}
