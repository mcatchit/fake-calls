package com.alenskaya.fakecalls.domain.contacts.model

/**
 * Information about contact to be saved.
 */
data class ContactToSave(
    val name: String,
    val phone: String,
    val photoUri: String?,
    val country: String
)
