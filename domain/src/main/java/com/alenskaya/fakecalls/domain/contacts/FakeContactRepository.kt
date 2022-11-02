package com.alenskaya.fakecalls.domain.contacts

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.RemoteRequestErrorCause
import com.alenskaya.fakecalls.domain.contacts.model.FakeContactsResponse

/**
 * Repository for requesting fake contacts data
 */
interface FakeContactRepository {

    /**
     * Requests [number] number of fake contacts from api
     * @return response with contacts list as payload
     */
    suspend fun getFakeUsers(number: Int): BaseResponse<FakeContactsResponse, RemoteRequestErrorCause>
}