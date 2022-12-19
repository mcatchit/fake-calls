package com.alenskaya.fakecalls.domain.calls.model

import java.util.Date

/**
 * Contains information about calls saved in DB
 */
data class SavedCall(

    /**
     * Unique identifier of the call
     */
    val id: Int,

    /**
     * Name of contact
     */
    val name: String,

    /**
     * Phone number
     */
    val phone: String,

    /**
     * Link to contact photo, can be null
     */
    val photoUrl: String?,

    /**
     * Date when the call will be executed
     */
    val date: Date,

    /**
     * Status of the call
     */
    val callStatus: CallStatus,

    /**
     * Unique request code of call intent.
     */
    val requestCode: Int
)
