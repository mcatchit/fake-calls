package com.alenskaya.fakecalls.data.remote.contacts.api

import com.alenskaya.fakecalls.data.remote.contacts.api.dto.FakeContactResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api which provides fake contacts data.
 */
interface FakeContactApi {

    /**
     * @return [amount] number of fake contacts
     */
    @GET(".")
    suspend fun getFakeContacts(@Query("results") amount: Int): Response<FakeContactResponseDto>
}
