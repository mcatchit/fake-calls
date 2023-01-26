package com.alenskaya.fakecalls.data.local.db

import com.alenskaya.fakecalls.data.local.catchDatabaseExceptions
import com.alenskaya.fakecalls.data.local.catchExceptions
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactDao
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactEntity
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactEntityToSavedContactConverter
import com.alenskaya.fakecalls.data.local.db.contacts.RemoteFakeContactToEntityConverter
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.contacts.ContactsLocalRepository
import com.alenskaya.fakecalls.domain.contacts.model.ContactToSave
import com.alenskaya.fakecalls.domain.contacts.model.SavedContact
import com.alenskaya.fakecalls.domain.contacts.model.SavedContactsResponse
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of fake contacts local repository based on the Room database
 */
internal class ContactsRoomRepository @Inject constructor(
    private val contactDao: FakeContactDao
) : ContactsLocalRepository {

    override suspend fun saveContacts(
        contacts: List<ContactToSave>
    ): BaseResponse<Unit, DatabaseError> = catchExceptions {
        contactDao.deleteAllContacts()

        for (contact in contacts) {
            contactDao.saveContact(RemoteFakeContactToEntityConverter.convert(contact))
        }

        BaseResponse.Success(Unit)
    }

    override suspend fun saveContact(contact: ContactToSave) = catchExceptions {
        BaseResponse.Success(
            contactDao.saveContact(RemoteFakeContactToEntityConverter.convert(contact)).toInt()
        )
    }

    override fun getSavedContacts(quantity: Int) = contactDao.getAllContacts()
        .map<List<FakeContactEntity>, BaseResponse.Success<SavedContactsResponse, DatabaseError>> { fakeContactEntities ->
            BaseResponse.Success(
                SavedContactsResponse(
                    fakeContactEntities.take(quantity).map { fakeContactEntity ->
                        FakeContactEntityToSavedContactConverter.convert(fakeContactEntity)
                    }
                )
            )
        }
        .catchDatabaseExceptions()

    override suspend fun getSavedContact(id: Int): BaseResponse<SavedContact, DatabaseError> {
        return catchExceptions {
            BaseResponse.Success(
                FakeContactEntityToSavedContactConverter.convert(contactDao.getContactById(id))
            )
        }
    }
}
