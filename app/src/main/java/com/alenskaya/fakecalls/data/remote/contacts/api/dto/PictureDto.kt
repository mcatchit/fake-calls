package com.alenskaya.fakecalls.data.remote.contacts.api.dto

import com.squareup.moshi.Json

/**
 * Fake contact's photo
 */
data class PictureDto(

    @Json(name = "large")
    val large: String,

    @Json(name = "medium")
    val medium: String,

    @Json(name = "thumbnail")
    val thumbnail: String
)
