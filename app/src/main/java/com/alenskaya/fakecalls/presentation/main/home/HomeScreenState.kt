package com.alenskaya.fakecalls.presentation.main.home

import com.alenskaya.fakecalls.presentation.mvi.UiState
import com.alenskaya.fakecalls.presentation.main.home.model.HomeScreenFakeContactModel

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
) : UiState {
    companion object {
        fun initial() = HomeScreenState(
            isLoading = true,
            contacts = listOf()
        )
    }
}
