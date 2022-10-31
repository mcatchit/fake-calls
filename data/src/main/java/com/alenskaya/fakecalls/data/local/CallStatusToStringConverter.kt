package com.alenskaya.fakecalls.data.local

import com.alenskaya.fakecalls.domain.calls.model.CallStatus
import com.alenskaya.fakecalls.util.TwoWayConverter

/**
 * Converts call status to string in order to store it in DB.
 * Supports reverse conversion.
 */
class CallStatusToStringConverter : TwoWayConverter<CallStatus, String> {

    override fun convert(input: CallStatus): String {
        return input.name
    }

    override fun convertBack(input: String): CallStatus {
        return CallStatus.values().find { it.name == input }
            ?: error("Unexpected status name '$input'")
    }
}
