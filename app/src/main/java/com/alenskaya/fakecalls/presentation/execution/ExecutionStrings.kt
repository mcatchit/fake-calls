package com.alenskaya.fakecalls.presentation.execution

import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.AppStrings
import javax.inject.Inject

/**
 * String resources for call execution.
 */
interface ExecutionStrings {
    fun incomingCall(): String
    fun photoDescription(): String
    fun accept(): String
    fun decline(): String
    fun missedCall(): String
}

class ExecutionStringsImpl @Inject constructor(
    private val appStrings: AppStrings
) : ExecutionStrings {

    override fun incomingCall(): String {
        return appStrings.getString(R.string.execution_screen_title)
    }

    override fun photoDescription(): String {
        return appStrings.getString(R.string.execution_screen_photo_description)
    }

    override fun accept(): String {
        return appStrings.getString(R.string.execution_screen_accept_description)
    }

    override fun decline(): String {
        return appStrings.getString(R.string.execution_screen_decline_description)
    }

    override fun missedCall(): String {
        return appStrings.getString(R.string.execution_screen_missed_call)
    }
}
