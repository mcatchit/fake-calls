package com.alenskaya.fakecalls.presentation.main.phonebook

import com.alenskaya.fakecalls.presentation.mvi.UiState
import com.alenskaya.fakecalls.presentation.phonebook.PhonebookContact

/**
 * Contains phonebook screen state.
 * Contacts must not be empty when [isLoading] false and [errorMessage] is null.
 */
data class PhonebookScreenState(
    val contacts: List<PhonebookContact>,
    val isLoading: Boolean,
    val errorMessage: String?
) : UiState {

    companion object {
        fun initial() = PhonebookScreenState(
            contacts = listOf(),
            isLoading = true,
            errorMessage = null
        )
    }
}