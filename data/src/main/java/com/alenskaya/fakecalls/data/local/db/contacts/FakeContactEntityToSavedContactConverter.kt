package com.alenskaya.fakecalls.data.local.db.contacts

import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContact
import com.alenskaya.fakecalls.util.Converter

/**
 * Converts database entity of fake contact to domain model
 */
internal object FakeContactEntityToSavedContactConverter :
    Converter<FakeContactEntity, SavedFakeContact> {

    override fun convert(input: FakeContactEntity) = with(input) {
        SavedFakeContact(
            id = id,
            name = name,
            phone = phone,
            photoUrl = photoUrl
        )
    }
}
