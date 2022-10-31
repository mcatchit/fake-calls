package com.alenskaya.fakecalls.domain.calls.repository

import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import java.util.Date

interface CallsRepository {
    suspend fun createCall(createNewCallRequest: CreateNewCallRequest)
    suspend fun getPlannedCalls(): List<SavedCall>
    suspend fun getCompletedCalls(): List<SavedCall>
}
