package com.alenskaya.fakecalls.domain.contacts.model

/**
 * Response to request for fake contacts
 */
data class RemoteFakeContactsResponse(
    val contacts: List<RemoteFakeContact>
)
