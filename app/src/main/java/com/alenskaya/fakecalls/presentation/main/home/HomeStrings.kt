package com.alenskaya.fakecalls.presentation.main.home

import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.AppStrings
import javax.inject.Inject

/**
 * String resources for home screen.
 */
interface HomeStrings {
    fun title(): String
    fun moreButton(): String
    fun createCustomButton(): String
    fun loadingSuggestions(): String
    fun noInternetConnectionMessage(): String
    fun failedToLoadContactsMessage(): String
}

class HomeStringsImpl @Inject constructor(
    private val appStrings: AppStrings
) : HomeStrings {

    override fun title(): String {
        return appStrings.getString(R.string.home_screen_title)
    }

    override fun moreButton(): String {
        return appStrings.getString(R.string.home_screen_more_button)
    }

    override fun createCustomButton(): String {
        return appStrings.getString(R.string.home_screen_create_custom_button)
    }

    override fun loadingSuggestions(): String {
        return appStrings.getString(R.string.home_screen_loading_suggestions)
    }

    override fun noInternetConnectionMessage(): String {
        return appStrings.getString(R.string.home_screen_no_internet_connection_message)
    }

    override fun failedToLoadContactsMessage(): String {
        return appStrings.getString(R.string.home_screen_failed_to_load_suggestions_message)
    }
}
