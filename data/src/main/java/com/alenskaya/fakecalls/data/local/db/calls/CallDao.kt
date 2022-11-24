package com.alenskaya.fakecalls.data.local.db.calls

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Makes requests to calls table
 */
@Dao
internal interface CallDao {

    /**
     * Requests list of all calls
     * @return list of all calls
     */
    @Query("SELECT * FROM calls")
    suspend fun getAllCalls(): List<CallEntity>

    /**
     * Adds a new [callEntity] to calls table
     */
    @Insert
    suspend fun createNewCall(callEntity: CallEntity)

    /**
     * Deletes a call with id = [id] from table
     */
    @Query("DELETE FROM calls WHERE id = :id")
    suspend fun deleteCall(id: Int)
}