package com.alenskaya.fakecalls.presentation.calls

import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import com.alenskaya.fakecalls.presentation.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts received call entity to ui model
 */
object SavedCallToCallModelConverter : Converter<SavedCall, CallsScreenCallModel> {

    override fun convert(input: SavedCall) = with(input) {
        CallsScreenCallModel(
            id = id,
            name = name,
            phone = phone,
            day = date.extractDay(),
            time = date.extractTime(),
            photoUrl = photoUrl
        )
    }
}
