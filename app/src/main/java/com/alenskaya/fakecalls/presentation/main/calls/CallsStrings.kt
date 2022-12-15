package com.alenskaya.fakecalls.presentation.main.calls

import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.AppStrings
import javax.inject.Inject

/**
 * String resources for calls screen.
 */
interface CallsStrings {
    fun repeatButtonDescription(): String
    fun deleteButtonDescription(): String
    fun editButtonDescription(): String
    fun title(): String
    fun scheduledTitle(): String
    fun completedTitle(): String
    fun loadingMessage(): String
}

class CallsStringsImpl @Inject constructor(
    private val appStrings: AppStrings
) : CallsStrings {

    override fun repeatButtonDescription(): String {
        return appStrings.getString(R.string.calls_screen_repeat_button_description)
    }

    override fun deleteButtonDescription(): String {
        return appStrings.getString(R.string.calls_screen_delete_button_description)
    }

    override fun editButtonDescription(): String {
        return appStrings.getString(R.string.calls_screen_edit_button_description)
    }

    override fun title(): String {
        return appStrings.getString(R.string.calls_screen_title)
    }

    override fun scheduledTitle(): String {
        return appStrings.getString(R.string.calls_screen_scheduled_title)
    }

    override fun completedTitle(): String {
        return appStrings.getString(R.string.calls_screen_completed_title)
    }

    override fun loadingMessage(): String {
        return appStrings.getString(R.string.calls_screen_loading_message)
    }
}