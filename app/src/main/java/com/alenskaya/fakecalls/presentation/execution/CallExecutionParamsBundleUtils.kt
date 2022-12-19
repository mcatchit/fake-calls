package com.alenskaya.fakecalls.presentation.execution

import android.os.Bundle
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams

private const val CALL_ID_BUNDLE_KEY = "call_id"
private const val CONTACT_NAME_BUNDLE_KEY = "contact_name"
private const val CONTACT_PHONE_BUNDLE_KEY = "contact_phone"
private const val CONTACT_PHOTO_BUNDLE_KEY = "contact_photo"
private const val REQUEST_CODE_BUNDLE_KEY = "contact_photo"

/**
 * Extracts call execution parameters from bundle.
 */
fun Bundle.extractCallExecutionParams() = CallExecutionParams(
    callId = getInt(CALL_ID_BUNDLE_KEY),
    name = getString(CONTACT_NAME_BUNDLE_KEY) ?: error("Name cannot be null"),
    phone = getString(CONTACT_PHONE_BUNDLE_KEY) ?: error("Phone cannot be null"),
    photoUrl = getString(CONTACT_PHOTO_BUNDLE_KEY),
    requestCode = getInt(REQUEST_CODE_BUNDLE_KEY)
)

/**
 * Puts all call execution parameters to bundle.
 */
fun CallExecutionParams.convertToBundle() = Bundle().apply {
    putInt(CALL_ID_BUNDLE_KEY, callId)
    putString(CONTACT_NAME_BUNDLE_KEY, name)
    putString(CONTACT_PHONE_BUNDLE_KEY, phone)
    putString(CONTACT_PHOTO_BUNDLE_KEY, photoUrl)
    putInt(REQUEST_CODE_BUNDLE_KEY, requestCode)
}
