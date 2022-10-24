package com.alenskaya.fakecalls.presentation

import android.content.Context
import android.widget.Toast

/**
 * Shows toast with given [message]
 */
fun Context.showToast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}
