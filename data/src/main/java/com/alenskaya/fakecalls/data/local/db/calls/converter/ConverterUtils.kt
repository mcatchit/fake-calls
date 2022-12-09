package com.alenskaya.fakecalls.data.local.db.calls.converter

import com.alenskaya.fakecalls.data.local.db.calls.CallEntity
import com.alenskaya.fakecalls.domain.calls.model.CallStatus
import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import com.alenskaya.fakecalls.domain.calls.model.UpdateCallRequest
import java.util.Date

/**
 * Converts create request to call entity.
 */
internal fun CreateNewCallRequest.toCallEntity() = CallEntity(
    contactName = name,
    contactPhone = phone,
    photoUrl = photoUrl,
    date = date.time,
    status = CallStatusToStringConverter.convert(CallStatus.SCHEDULED)
)

/**
 * Converts update request to call entity.
 */
internal fun UpdateCallRequest.toCallEntity() = CallEntity(
    id = callId,
    contactName = name,
    contactPhone = phone,
    photoUrl = photoUrl,
    date = date.time,
    status = CallStatusToStringConverter.convert(status)
)

/**
 * Converts call entity to saved call.
 */
internal fun CallEntity.toSavedCall() = SavedCall(
    id = id,
    name = contactName,
    phone = contactPhone,
    photoUrl = photoUrl,
    date = Date(date),
    callStatus = CallStatusToStringConverter.convertBack(status)
)
