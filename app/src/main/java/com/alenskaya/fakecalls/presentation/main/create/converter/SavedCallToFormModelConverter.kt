package com.alenskaya.fakecalls.presentation.main.create.converter

import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import com.alenskaya.fakecalls.presentation.main.create.model.CreateCallScreenFormModel
import com.alenskaya.fakecalls.util.Converter

/**
 * Transforms saved call to form model depending on mode.
 *
 * @property showDate - whether to show call date.
 * If isEditing is false, date will be null.
 */
class SavedCallToFormModelConverter(
    private val showDate: Boolean
) : Converter<SavedCall, CreateCallScreenFormModel> {

    override fun convert(input: SavedCall) = with(input) {
        CreateCallScreenFormModel(
            photo = photoUrl,
            name = name,
            phone = phone,
            date = if (showDate) date else null
        )
    }
}
