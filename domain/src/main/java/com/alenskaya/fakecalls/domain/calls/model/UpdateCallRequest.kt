package com.alenskaya.fakecalls.domain.calls.model

import java.util.Date

/**
 * Request to update an existing call.
 */
data class UpdateCallRequest(
    /**
     * Call id
     */
    val callId: Int,

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
     * Call status
     */
    val status: CallStatus
)
