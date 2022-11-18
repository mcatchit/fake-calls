package com.alenskaya.fakecalls.data.local.db.contacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Makes requests to fake_contacts table
 */
@Dao
internal interface FakeContactDao {

    /**
     * Requests list of all fake contacts
     * @return list of all fake contacts
     */
    @Query("SELECT * FROM fake_contacts")
    suspend fun getAllContacts(): List<FakeContactEntity>

    /**
     * Requests list of all fake contacts
     * @return list of all fake contacts
     */
    @Query("SELECT * FROM fake_contacts WHERE id = :id")
    suspend fun getContactById(id: Int): FakeContactEntity

    /**
     * Adds a new [fakeContactEntity] to fake_contacts table
     */
    @Insert
    suspend fun saveContact(fakeContactEntity: FakeContactEntity)

    /**
     * Deletes all contacts records
     */
    @Query("DELETE FROM fake_contacts")
    suspend fun deleteAllContacts()
}
