package com.alenskaya.fakecalls.domain.contacts

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.RemoteRequestErrorCause
import com.alenskaya.fakecalls.domain.contacts.model.RemoteFakeContactsResponse

/**
 * Repository for requesting fake contacts data
 */
interface FakeContactRemoteRepository {

    /**
     * Requests [number] number of fake contacts from api
     * @return response with contacts list as payload
     */
    suspend fun getFakeUsers(number: Int): BaseResponse<RemoteFakeContactsResponse, RemoteRequestErrorCause>
}
