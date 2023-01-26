package com.alenskaya.fakecalls.data.local.db.contacts

import com.alenskaya.fakecalls.domain.contacts.model.SavedContact
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts database entity of fake contact to domain model
 */
internal object FakeContactEntityToSavedContactConverter :
    Converter<FakeContactEntity, SavedContact> {

    override fun convert(input: FakeContactEntity) = with(input) {
        SavedContact(
            id = id,
            name = name,
            phone = phone,
            country = country,
            photoUrl = photoUrl
        )
    }
}
