package com.alenskaya.fakecalls.domain.calls

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import com.alenskaya.fakecalls.domain.calls.model.UpdateCallRequest
import kotlinx.coroutines.flow.Flow

/**
 * Repository for handling calls data.
 */
interface CallsRepository {

    /**
     * Request to save a new call with data from [createNewCallRequest].
     * @return [BaseResponse.Success] with saved call in case of success, [BaseResponse.Error] otherwise
     */
    suspend fun createCall(
        createNewCallRequest: CreateNewCallRequest
    ): BaseResponse<SavedCall, DatabaseError>

    /**
     * Requests list of saved calls.
     * @return flow which emits [BaseResponse.Success] with sorted by date list of calls as a payload in case of success,
     * [BaseResponse.Error] otherwise
     */
    fun getSavedCalls(): Flow<BaseResponse<List<SavedCall>, DatabaseError>>

    /**
     * Requests call with given id
     * @param callId - call identifier.
     * @return [BaseResponse.Success] with saved call as a payload in case of success,
     * [BaseResponse.Error] otherwise
     */
    suspend fun getSavedCallById(callId: Int): BaseResponse<SavedCall, DatabaseError>

    /**
     * Updates call according to [updateCallRequest].
     * @return empty [BaseResponse.Success] in case of success, [BaseResponse.Error] otherwise.
     */
    suspend fun updateCall(
        updateCallRequest: UpdateCallRequest
    ): BaseResponse<Unit, DatabaseError>

    /**
     * Deletes call with given id.
     * @param callId - call identifier.
     */
    suspend fun deleteCall(callId: Int): BaseResponse<Unit, DatabaseError>

    /**
     * Updates status of call with id = [callId] to COMPLETED.
     * @return empty [BaseResponse.Success] in case of success, [BaseResponse.Error] otherwise.
     */
    suspend fun markCallAsCompleted(callId: Int): BaseResponse<Unit, DatabaseError>
}
