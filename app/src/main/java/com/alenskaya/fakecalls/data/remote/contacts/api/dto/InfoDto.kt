package com.alenskaya.fakecalls.data.remote.contacts.api.dto

import com.squareup.moshi.Json

/**
 * Contains technical info about response from FakeContactApi
 */
data class InfoDto(

    @Json(name = "page")
    val page: Int,

    @Json(name = "results")
    val results: Int,

    @Json(name = "seed")
    val seed: String,

    @Json(name = "version")
    val version: String
)
