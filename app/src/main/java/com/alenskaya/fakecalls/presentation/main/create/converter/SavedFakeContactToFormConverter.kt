package com.alenskaya.fakecalls.presentation.main.create.converter

import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContact
import com.alenskaya.fakecalls.presentation.main.create.model.CreateCallScreenFormModel
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts fake contact to form model
 */
object SavedFakeContactToFormConverter : Converter<SavedFakeContact, CreateCallScreenFormModel> {

    override fun convert(input: SavedFakeContact) = with(input) {
        CreateCallScreenFormModel(
            photo = photoUrl,
            name = name,
            phone = phone
        )
    }
}
