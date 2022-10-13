package com.alenskaya.fakecalls.data.remote.contacts.api.dto

import com.squareup.moshi.Json

/**
 * Api response to fake contact data request
 */
data class FakeContactResponseDto(

    @Json(name = "results")
    val results: List<FakeContactDto>,

    @Json(name = "info")
    val info: InfoDto
)
