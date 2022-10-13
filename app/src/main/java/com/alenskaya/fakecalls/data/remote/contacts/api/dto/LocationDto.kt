package com.alenskaya.fakecalls.data.remote.contacts.api.dto

import com.squareup.moshi.Json

/**
 * Info about fake contact's location
 */
data class LocationDto(

    @Json(name = "city")
    val city: String,

    @Json(name = "country")
    val country: String
)
