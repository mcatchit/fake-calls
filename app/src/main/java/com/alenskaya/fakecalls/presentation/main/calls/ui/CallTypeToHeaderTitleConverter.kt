package com.alenskaya.fakecalls.presentation.main.calls.ui

import com.alenskaya.fakecalls.presentation.main.calls.CallsStrings
import com.alenskaya.fakecalls.presentation.main.calls.model.CallType
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts call type to group title.
 */
class CallTypeToHeaderTitleConverter(
    private val callsStrings: CallsStrings
) : Converter<CallType, String> {

    override fun convert(input: CallType): String {
        return when (input) {
            CallType.SCHEDULED -> callsStrings.scheduledTitle()
            CallType.COMPLETED -> callsStrings.completedTitle()
        }
    }
}
