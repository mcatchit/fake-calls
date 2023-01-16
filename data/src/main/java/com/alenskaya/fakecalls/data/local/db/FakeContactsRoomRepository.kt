package com.alenskaya.fakecalls.data.local.db

import com.alenskaya.fakecalls.data.local.catchDatabaseExceptions
import com.alenskaya.fakecalls.data.local.catchExceptions
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactDao
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactEntity
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactEntityToSavedContactConverter
import com.alenskaya.fakecalls.data.local.db.contacts.RemoteFakeContactToEntityConverter
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.contacts.FakeContactLocalRepository
import com.alenskaya.fakecalls.domain.contacts.model.RemoteFakeContact
import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContact
import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContactsResponse
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of fake contacts local repository based on the Room database
 */
internal class FakeContactsRoomRepository @Inject constructor(
    private val contactDao: FakeContactDao
) : FakeContactLocalRepository {

    override suspend fun saveFakeContacts(
        contacts: List<RemoteFakeContact>
    ): BaseResponse<Unit, DatabaseError> = catchExceptions {
        contactDao.deleteAllContacts()

        for (contact in contacts) {
            contactDao.saveContact(RemoteFakeContactToEntityConverter.convert(contact))
        }

        BaseResponse.Success(Unit)
    }

    override fun getFakeContacts() = contactDao.getAllContacts()
        .map<List<FakeContactEntity>, BaseResponse.Success<SavedFakeContactsResponse, DatabaseError>> { fakeContactEntities ->
            BaseResponse.Success(
                SavedFakeContactsResponse(
                    fakeContactEntities.map { fakeContactEntity ->
                        FakeContactEntityToSavedContactConverter.convert(fakeContactEntity)
                    }
                )
            )
        }
        .catchDatabaseExceptions()

    override suspend fun getFakeContact(id: Int): BaseResponse<SavedFakeContact, DatabaseError> {
        return catchExceptions {
            BaseResponse.Success(
                FakeContactEntityToSavedContactConverter.convert(contactDao.getContactById(id))
            )
        }
    }
}
