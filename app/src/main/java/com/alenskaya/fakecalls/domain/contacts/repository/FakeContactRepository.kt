package com.alenskaya.fakecalls.domain.contacts.repository

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.contacts.model.FakeContactsResponse

/**
 * Repository for requesting fake contacts data
 */
interface FakeContactRepository {

    /**
     * Requests [amount] number of fake contacts from api
     * @return response with contacts list as payload
     */
    suspend fun getFakeUsers(amount: Int): BaseResponse<FakeContactsResponse>
}
