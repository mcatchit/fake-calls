package com.alenskaya.fakecalls.data.local.db.contacts

import com.alenskaya.fakecalls.domain.contacts.model.RemoteFakeContact
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts remote model of fake contact to database entity
 */
internal object RemoteFakeContactToEntityConverter :
    Converter<RemoteFakeContact, FakeContactEntity> {

    override fun convert(input: RemoteFakeContact) = with(input) {
        FakeContactEntity(
            name = name,
            phone = phone,
            photoUrl = photoUrl,
            country = country
        )
    }
}
