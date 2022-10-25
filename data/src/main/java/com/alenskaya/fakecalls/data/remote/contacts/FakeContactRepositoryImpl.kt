package com.alenskaya.fakecalls.data.remote.contacts

import com.alenskaya.fakecalls.data.remote.catchExceptions
import com.alenskaya.fakecalls.data.remote.toBaseResponse
import com.alenskaya.fakecalls.data.remote.contacts.api.FakeContactApi
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.contacts.repository.FakeContactRepository
import com.alenskaya.fakecalls.domain.contacts.model.FakeContactsResponse
import javax.inject.Inject

/**
 * Implementation of fake contacts repository which refers to [FakeContactApi].
 */
internal class FakeContactRepositoryImpl @Inject constructor(
    private val fakeContactApi: FakeContactApi
) : FakeContactRepository {

    override suspend fun getFakeUsers(amount: Int): BaseResponse<FakeContactsResponse> {
        return catchExceptions {
            fakeContactApi.getFakeContacts(amount)
                .toBaseResponse(FakeContactResponseDtoToDomainResponseConverter())
        }
    }
}
