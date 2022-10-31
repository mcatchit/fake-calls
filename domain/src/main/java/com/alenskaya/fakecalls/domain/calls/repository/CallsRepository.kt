package com.alenskaya.fakecalls.domain.calls.repository

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import java.util.Date

interface CallsRepository {
    suspend fun createCall(createNewCallRequest: CreateNewCallRequest): BaseResponse<Unit, DatabaseError>
    suspend fun getPlannedCalls(): BaseResponse<List<SavedCall>, DatabaseError>
    suspend fun getCompletedCalls(): BaseResponse<List<SavedCall>, DatabaseError>
}
