package com.alenskaya.fakecalls.data.remote.contacts

import com.alenskaya.fakecalls.util.Converter
import com.alenskaya.fakecalls.data.remote.contacts.api.dto.FakeContactDto
import com.alenskaya.fakecalls.domain.contacts.model.RemoteFakeContact

/**
 * Converts contact dto to domain model.
 */
object FakeContactDtoToDomainModelConverter : Converter<FakeContactDto, RemoteFakeContact> {

    override fun convert(input: FakeContactDto) = with(input) {
        RemoteFakeContact(
            name = "${name.firstName} ${name.lastName}",
            phone = phone,
            photoUrl = picture.large,
            country = location.country
        )
    }
}
