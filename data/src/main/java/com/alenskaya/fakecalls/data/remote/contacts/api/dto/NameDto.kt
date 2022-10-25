package com.alenskaya.fakecalls.data.remote.contacts.api.dto

import com.squareup.moshi.Json

/**
 * Info about fake contact's name
 */
data class NameDto(

    @Json(name = "first")
    val firstName: String,

    @Json(name = "last")
    val lastName: String
)
