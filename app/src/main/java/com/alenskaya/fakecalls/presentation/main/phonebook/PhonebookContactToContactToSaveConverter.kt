package com.alenskaya.fakecalls.presentation.main.phonebook

import com.alenskaya.fakecalls.domain.contacts.model.ContactToSave
import com.alenskaya.fakecalls.presentation.phonebook.PhonebookContact
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts phonebook contact to save contact request.
 */
object PhonebookContactToContactToSaveConverter : Converter<PhonebookContact, ContactToSave> {

    override fun convert(input: PhonebookContact) = with(input) {
        ContactToSave(
            name = name,
            phone = phone,
            photoUri = photoUri,
            country = "" //TODO add country to parameters
        )
    }
}
