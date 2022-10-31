package com.alenskaya.fakecalls.data.local.db

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
     * Requests calls with specific [status]
     * @return list of calls with status = [status]
     */
    @Query("SELECT * FROM calls WHERE status = :status")
    suspend fun getCallsWithStatus(status: String): List<CallEntity>

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
