package com.alenskaya.fakecalls.domain.contacts.model

/**
 * Model of locally saved fake contact
 */
data class SavedFakeContact(

    /**
     * Unique contact identifier.
     */
    val id: Int,

    /**
     * Contact name.
     */
    val name: String,

    /**
     * Contact phone.
     */
    val phone: String,

    /**
     * Contact photo.
     */
    val photoUrl: String?
)
