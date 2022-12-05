package com.alenskaya.fakecalls.domain.contacts.model

/**
 * Information about fake contact loaded from api
 */
data class RemoteFakeContact(
    val name: String,
    val phone: String,
    val photoUrl: String,
    val country: String
)
