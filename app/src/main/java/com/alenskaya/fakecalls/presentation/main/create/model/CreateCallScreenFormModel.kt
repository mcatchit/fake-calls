package com.alenskaya.fakecalls.presentation.main.create.model

import com.alenskaya.fakecalls.presentation.main.convertToString
import java.util.Date

/**
 * Model of Create call screen input fields.
 */
data class CreateCallScreenFormModel(
    val photo: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val date: Date? = null
) {
    val displayableDate: String? = date?.convertToString()

    companion object {
        fun initial() = CreateCallScreenFormModel()
    }
}

/**
 * Util function which validates if the form is properly filled.
 */
fun CreateCallScreenFormModel.isFilled() =
    !name.isNullOrEmpty() && !phone.isNullOrEmpty() && date != null
