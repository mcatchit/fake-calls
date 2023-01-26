package com.alenskaya.fakecalls.data.local.db.contacts

import com.alenskaya.fakecalls.domain.contacts.model.ContactToSave
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts remote model of fake contact to database entity
 */
internal object RemoteFakeContactToEntityConverter :
    Converter<ContactToSave, FakeContactEntity> {

    override fun convert(input: ContactToSave) = with(input) {
        FakeContactEntity(
            name = name,
            phone = phone,
            photoUrl = photoUri,
            country = country
        )
    }
}
