package com.alenskaya.fakecalls.domain.contacts

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.RemoteRequestErrorCause
import com.alenskaya.fakecalls.domain.contacts.model.RemoteFakeContact
import com.alenskaya.fakecalls.domain.contacts.model.RemoteFakeContactsResponse
import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContact
import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContactsResponse

/**
 * Repository for manipulating fake contacts data locally.
 */
interface FakeContactLocalRepository {

    /**
     * Saves loaded from remote repository fake contacts locally.
     * @param contacts - loaded contacts.
     * @return list of saved contacts.
     */
    suspend fun saveFakeContacts(
        contacts: List<RemoteFakeContact>
    ): BaseResponse<SavedFakeContactsResponse, DatabaseError>

    /**
     * Returns all saved contacts.
     * @return list of saved contacts.
     */
    suspend fun getFakeContacts(): BaseResponse<SavedFakeContactsResponse, DatabaseError>

    /**
     * Finds contact with passed [id].
     * @param id - id of required contact.
     * @return saved fake contact.
     */
    suspend fun getFakeContact(id: Int): BaseResponse<SavedFakeContact, DatabaseError>
}
