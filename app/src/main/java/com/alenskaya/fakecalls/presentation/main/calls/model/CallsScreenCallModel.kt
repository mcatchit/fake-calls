package com.alenskaya.fakecalls.presentation.main.calls.model

/**
 * Model of call item displayed in common lists of calls.
 */
data class CallsScreenCallModel(

    /**
     * Unique call identifier.
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
     * Day when the call will be executed.
     */
    val day: String,

    /**
     * Time when the call will be executed.
     */
    val time: String,

    /**
     * Url of the photo, can be null when the photo is not set.
     */
    val photoUrl: String?
)
