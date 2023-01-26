package com.alenskaya.fakecalls.data.remote.contacts

import com.alenskaya.fakecalls.util.Converter
import com.alenskaya.fakecalls.data.remote.contacts.api.dto.FakeContactDto
import com.alenskaya.fakecalls.domain.contacts.model.ContactToSave

/**
 * Converts contact dto to domain model.
 */
object FakeContactDtoToDomainModelConverter : Converter<FakeContactDto, ContactToSave> {

    override fun convert(input: FakeContactDto) = with(input) {
        ContactToSave(
            name = "${name.firstName} ${name.lastName}",
            phone = phone,
            photoUri = picture.large,
            country = location.country
        )
    }
}
