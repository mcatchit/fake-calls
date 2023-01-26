package com.alenskaya.fakecalls.domain.contacts.model

/**
 * Wrapper for local response for get all saved contacts request.
 */
data class SavedContactsResponse(

    /**
     * List of all saved contacts.
     */
    val contacts: List<SavedContact>
)
