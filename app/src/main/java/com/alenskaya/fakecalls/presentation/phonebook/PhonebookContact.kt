package com.alenskaya.fakecalls.presentation.phonebook

/**
 * Class for contact data retrieved from phone book.
 */
data class PhonebookContact(
    val id: String,
    val name: String,
    val phone: String,
    val photoUri: String?
)
