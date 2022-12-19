package com.alenskaya.fakecalls.domain.calls.model

import java.util.Date

/**
 * Request for creating a new call
 */
data class CreateNewCallRequest(

    /**
     * Contact name
     */
    val name: String,

    /**
     * Contact phone
     */
    val phone: String,

    /**
     * Date when the call will be executed
     */
    val date: Date,

    /**
     * Link to contact photo. Can be null when photo is not specified
     */
    val photoUrl: String?,

    /**
     * Call execution intent request code.
     */
    val requestCode: Int
)
