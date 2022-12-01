package com.alenskaya.fakecalls.data.local.db.calls

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alenskaya.fakecalls.domain.calls.model.CallStatus

/**
 * Makes requests to calls table.
 */
@Dao
internal interface CallDao {

    /**
     * Requests list of all calls.
     * @return list of all calls.
     */
    @Query("SELECT * FROM calls")
    suspend fun getAllCalls(): List<CallEntity>

    /**
     * Finds call with passed [id].
     */
    @Query("SELECT * FROM calls WHERE id = :id")
    suspend fun getCallById(id: Int): CallEntity

    /**
     * Adds a new [callEntity] to calls table.
     * @return id of inserted item.
     */
    @Insert
    suspend fun createNewCall(callEntity: CallEntity): Long

    /**
     * Deletes a call with id = [id] from table.
     */
    @Query("DELETE FROM calls WHERE id = :id")
    suspend fun deleteCall(id: Int)

    /**
     * Updates [callEntity].
     */
    @Update
    suspend fun updateCall(callEntity: CallEntity)
}
