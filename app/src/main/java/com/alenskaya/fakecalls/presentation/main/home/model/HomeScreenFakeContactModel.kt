package com.alenskaya.fakecalls.presentation.main.home.model

/**
 * Ui model of fake contact.
 * Has additional property - [isHintVisible].
 */
data class HomeScreenFakeContactModel(

    /**
     * Unique identifier of contact.
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
     * Link to contact photo.
     */
    val photoUrl: String?,

    /**
     * Contact country.
     */
    val country: String,

    /**
     * Visibility of hint.
     */
    val isHintVisible: Boolean = false
)
