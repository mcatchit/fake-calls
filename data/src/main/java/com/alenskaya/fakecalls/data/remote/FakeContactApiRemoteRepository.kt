package com.alenskaya.fakecalls.data.remote

import com.alenskaya.fakecalls.data.remote.contacts.FakeContactResponseDtoToDomainResponseConverter
import com.alenskaya.fakecalls.data.remote.contacts.api.FakeContactApi
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.RemoteRequestErrorCause
import com.alenskaya.fakecalls.domain.contacts.FakeContactRemoteRepository
import com.alenskaya.fakecalls.domain.contacts.model.RemoteFakeContactsResponse
import javax.inject.Inject

/**
 * Implementation of fake contacts repository which refers to [FakeContactApi].
 */
internal class FakeContactApiRemoteRepository @Inject constructor(
    private val fakeContactApi: FakeContactApi
) : FakeContactRemoteRepository {

    override suspend fun getFakeUsers(
        number: Int
    ): BaseResponse<RemoteFakeContactsResponse, RemoteRequestErrorCause> {
        return catchExceptions {
            fakeContactApi.getFakeContacts(number)
                .toBaseResponse(FakeContactResponseDtoToDomainResponseConverter())
        }
    }
}
