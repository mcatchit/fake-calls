package com.alenskaya.fakecalls.data.local.db.contacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

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
    fun getAllContacts(): Flow<List<FakeContactEntity>>

    /**
     * Requests list of all fake contacts
     * @return list of all fake contacts
     */
    @Query("SELECT * FROM fake_contacts WHERE id = :id")
    suspend fun getContactById(id: Int): FakeContactEntity

    /**
     * Adds a new [fakeContactEntity] to fake_contacts table
     * @return id of inserted row
     */
    @Insert
    suspend fun saveContact(fakeContactEntity: FakeContactEntity): Long

    /**
     * Deletes all contacts records
     */
    @Query("DELETE FROM fake_contacts")
    suspend fun deleteAllContacts()
}
