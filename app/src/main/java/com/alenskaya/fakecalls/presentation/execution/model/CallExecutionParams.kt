package com.alenskaya.fakecalls.presentation.execution.model

/**
 * Parameters for call execution.
 */
data class CallExecutionParams(

    /**
     * Call identifier from db.
     */
    val callId: Int,

    /**
     * Name of calling person.
     */
    val name: String,

    /**
     * Phone of calling person.
     */
    val phone: String,

    /**
     * Photo url of calling person.
     */
    val photoUrl: String?
)
