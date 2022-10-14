package com.alenskaya.fakecalls.data.remote.contacts

import com.alenskaya.fakecalls.utils.Converter
import com.alenskaya.fakecalls.data.remote.contacts.api.dto.FakeContactDto
import com.alenskaya.fakecalls.domain.contacts.model.FakeContact

/**
 * Converts contact dto to domain model.
 */
object FakeContactDtoToDomainModelConverter : Converter<FakeContactDto, FakeContact> {

    override fun convert(input: FakeContactDto) = with(input) {
        FakeContact(
            name = "${name.firstName} ${name.lastName}",
            phone = phone,
            photoUrl = picture.large
        )
    }
}
