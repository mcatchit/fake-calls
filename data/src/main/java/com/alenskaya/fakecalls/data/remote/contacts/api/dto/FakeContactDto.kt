package com.alenskaya.fakecalls.data.remote.contacts.api.dto

import com.squareup.moshi.Json

/**
 * Dto model of one fake contact
 */
data class FakeContactDto(

    @Json(name = "email")
    val email: String,

    @Json(name = "gender")
    val gender: String,

    @Json(name = "location")
    val location: LocationDto,

    @Json(name = "name")
    val name: NameDto,

    @Json(name = "phone")
    val phone: String,

    @Json(name = "picture")
    val picture: PictureDto
)
