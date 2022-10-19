package com.alenskaya.fakecalls.presentation.home

import com.alenskaya.fakecalls.domain.contacts.model.FakeContact
import com.alenskaya.fakecalls.presentation.UiState

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
    val contacts: List<FakeContact>,

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
