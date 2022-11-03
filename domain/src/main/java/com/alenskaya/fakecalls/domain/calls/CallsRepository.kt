package com.alenskaya.fakecalls.domain.calls

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.calls.model.SavedCall

/**
 * Repository for handling calls data.
 */
interface CallsRepository {

    /**
     * Request to save a new call with data from [createNewCallRequest].
     * @return empty [BaseResponse.Success] in case of success, [BaseResponse.Error] otherwise
     */
    suspend fun createCall(
        createNewCallRequest: CreateNewCallRequest
    ): BaseResponse<Unit, DatabaseError>

    /**
     * Requests list of saved calls.
     * @return [BaseResponse.Success] with sorted by date list of calls as a payload in case of success,
     * [BaseResponse.Error] otherwise
     */
    suspend fun getSavedCalls(): BaseResponse<List<SavedCall>, DatabaseError>
}
