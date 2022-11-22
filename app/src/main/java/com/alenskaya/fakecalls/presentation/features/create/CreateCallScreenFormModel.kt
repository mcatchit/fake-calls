package com.alenskaya.fakecalls.presentation.features.create

import java.util.Date

/**
 * Model of Create call screen input fields.
 */
data class CreateCallScreenFormInput(
    val photo: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val date: Date? = null
)

/**
 * Util function which validates if the form is properly filled.
 */
fun CreateCallScreenFormInput.isFilled() =
    !name.isNullOrEmpty() && !phone.isNullOrEmpty() && date != null
