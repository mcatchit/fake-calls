package com.alenskaya.fakecalls.domain.contacts

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.contacts.model.ContactToSave
import com.alenskaya.fakecalls.domain.contacts.model.SavedContact
import com.alenskaya.fakecalls.domain.contacts.model.SavedContactsResponse
import kotlinx.coroutines.flow.Flow

/**
 * Repository for manipulating fake contacts data locally.
 */
interface ContactsLocalRepository {

    /**
     * Saves list of contacts locally.
     */
    suspend fun saveContacts(
        contacts: List<ContactToSave>
    ): BaseResponse<Unit, DatabaseError>

    /**
     * Saves single contact locally.
     */
    suspend fun saveContact(contact: ContactToSave): BaseResponse<Int, DatabaseError>

    /**
     * Returns all saved contacts.
     * @return flow which emits list of saved contacts.
     */
    fun getSavedContacts(quantity: Int): Flow<BaseResponse<SavedContactsResponse, DatabaseError>>

    /**
     * Finds contact with passed [id].
     * @param id - id of required contact.
     * @return saved fake contact.
     */
    suspend fun getSavedContact(id: Int): BaseResponse<SavedContact, DatabaseError>
}
