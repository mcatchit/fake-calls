package com.alenskaya.fakecalls.presentation.execution

import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.AppStrings
import javax.inject.Inject

/**
 * String resources for call execution.
 */
interface ExecutionStrings {
    fun incomingCall(): String
    fun ongoingCall(): String
    fun callExecutionTimeIcon(): String
    fun photoDescription(): String
    fun accept(): String
    fun decline(): String
    fun missedCall(): String
    fun callCompleted(): String
}

class ExecutionStringsImpl @Inject constructor(
    private val appStrings: AppStrings
) : ExecutionStrings {

    override fun incomingCall(): String {
        return appStrings.getString(R.string.execution_incoming_call_screen_title)
    }

    override fun ongoingCall(): String {
        return appStrings.getString(R.string.execution_ongoing_call_screen_title)
    }

    override fun callExecutionTimeIcon(): String {
        return appStrings.getString(R.string.execution_ongoing_call_execution_time_icon)
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

    override fun callCompleted(): String {
        return appStrings.getString(R.string.execution_screen_call_completed)
    }
}
