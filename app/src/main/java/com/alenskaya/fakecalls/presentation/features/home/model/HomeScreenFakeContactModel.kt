package com.alenskaya.fakecalls.presentation.features.home.model

/**
 * Ui model of fake contact.
 * Has two additional properties - [id] and [isHintVisible].
 */
data class HomeScreenFakeContactModel(

    /**
     * Unique identifier of contact.
     */
    val id: Int,

    /**
     * Contact name
     */
    val name: String,

    /**
     * Contact phone
     */
    val phone: String,

    /**
     * Link to contact photo
     */
    val photoUrl: String?,

    /**
     * Visibility of hint.
     */
    val isHintVisible: Boolean = false
)
