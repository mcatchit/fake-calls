package com.alenskaya.fakecalls.presentation.main.phonebook

import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.AppStrings
import javax.inject.Inject

/**
 * String resources for phonebook screen.
 */
interface PhonebookStrings {
    fun title(): String
    fun loading(): String
    fun empty(): String
    fun errorMessage(): String
    fun navigateBack(): String
}

class PhonebookStringsImpl @Inject constructor(
    private val appStrings: AppStrings
) : PhonebookStrings {

    override fun title() = appStrings.getString(R.string.phonebook_screen_title)

    override fun loading() = appStrings.getString(R.string.phonebook_screen_loading_message)

    override fun empty() = appStrings.getString(R.string.phonebook_screen_empty_message)

    override fun errorMessage() = appStrings.getString(R.string.phonebook_screen_error_message)

    override fun navigateBack() =
        appStrings.getString(R.string.phonebook_screen_navigate_back_description)
}
