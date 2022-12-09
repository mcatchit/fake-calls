package com.alenskaya.fakecalls.presentation.main.create.model

import java.util.Date

/**
 * Data to create a new call.
 */
data class CreateNewCallData(
    val name: String,
    val phone: String,
    val date: Date,
    val photoUrl: String?
)
