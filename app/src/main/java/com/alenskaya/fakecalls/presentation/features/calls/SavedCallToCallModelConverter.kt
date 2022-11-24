package com.alenskaya.fakecalls.presentation.features.calls

import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import com.alenskaya.fakecalls.presentation.features.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.presentation.features.extractDayWithMonth
import com.alenskaya.fakecalls.presentation.features.extractTime
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
            day = date.extractDayWithMonth(),
            time = date.extractTime(),
            photoUrl = photoUrl
        )
    }
}
