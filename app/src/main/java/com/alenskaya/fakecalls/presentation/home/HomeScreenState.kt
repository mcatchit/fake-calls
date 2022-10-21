package com.alenskaya.fakecalls.presentation.home

import com.alenskaya.fakecalls.presentation.UiState
import com.alenskaya.fakecalls.presentation.home.model.HomeScreenFakeContactModel

/**
 * Ui state of HomeScreen
 */
data class HomeScreenState(

    /**
     * Indicator of showing loading progress
     */
    val isLoading: Boolean,

    /**
     * List of displayable contacts
     */
    val contacts: List<HomeScreenFakeContactModel>,

    /**
     * Error toast text
     */
    val message: String? = null
) : UiState {
    companion object {
        fun initial() = HomeScreenState(
            isLoading = true,
            contacts = listOf(),
            message = null
        )
    }
}
