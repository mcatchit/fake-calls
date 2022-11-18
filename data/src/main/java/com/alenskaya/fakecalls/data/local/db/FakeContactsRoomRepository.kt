package com.alenskaya.fakecalls.data.local.db

import com.alenskaya.fakecalls.data.local.catchExceptions
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactDao
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactEntityToSavedContactConverter
import com.alenskaya.fakecalls.data.local.db.contacts.RemoteFakeContactToEntityConverter
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.contacts.FakeContactLocalRepository
import com.alenskaya.fakecalls.domain.contacts.model.RemoteFakeContact
import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContact
import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContactsResponse
import javax.inject.Inject

/**
 * Implementation of fake contacts local repository based on the Room database
 */
internal class FakeContactsRoomRepository @Inject constructor(
    private val contactDao: FakeContactDao
) : FakeContactLocalRepository {

    override suspend fun saveFakeContacts(
        contacts: List<RemoteFakeContact>
    ): BaseResponse<SavedFakeContactsResponse, DatabaseError> = catchExceptions {

        contactDao.deleteAllContacts()

        for (contact in contacts) {
            contactDao.saveContact(RemoteFakeContactToEntityConverter.convert(contact))
        }

        getFakeContactsUnsafely()
    }

    override suspend fun getFakeContacts(): BaseResponse<SavedFakeContactsResponse, DatabaseError> {
        return catchExceptions {
            getFakeContactsUnsafely()
        }
    }

    override suspend fun getFakeContact(id: Int): BaseResponse<SavedFakeContact, DatabaseError> {
        return catchExceptions {
            BaseResponse.Success(
                FakeContactEntityToSavedContactConverter.convert(contactDao.getContactById(id))
            )
        }
    }

    private suspend fun getFakeContactsUnsafely(): BaseResponse<SavedFakeContactsResponse, DatabaseError> {
        return BaseResponse.Success(
            SavedFakeContactsResponse(
                contactDao.getAllContacts().map { fakeContactEntity ->
                    FakeContactEntityToSavedContactConverter.convert(fakeContactEntity)
                }
            )
        )
    }
}
