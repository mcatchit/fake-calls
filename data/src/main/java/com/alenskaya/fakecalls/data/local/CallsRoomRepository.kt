package com.alenskaya.fakecalls.data.local

import com.alenskaya.fakecalls.data.local.db.CallDao
import com.alenskaya.fakecalls.data.local.db.CallEntity
import com.alenskaya.fakecalls.domain.calls.model.CallStatus
import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import com.alenskaya.fakecalls.domain.calls.repository.CallsRepository
import java.util.Date

/**
 * Calls repository implementation based on the Room database
 */
class CallsRoomRepository(
    private val callDao: CallDao
) : CallsRepository {

    override suspend fun createCall(createNewCallRequest: CreateNewCallRequest) =
        with(createNewCallRequest) {
            callDao.createNewCall(
                CallEntity(
                    contactName = name,
                    contactPhone = phone,
                    date = date.time,
                    photoUrl = photoUrl,
                    status = statusConverter.convert(CallStatus.SCHEDULED)
                )
            )
        }

    override suspend fun getPlannedCalls(): List<SavedCall> {
        return getCallsWithStatus(CallStatus.SCHEDULED)
    }

    override suspend fun getCompletedCalls(): List<SavedCall> {
        return getCallsWithStatus(CallStatus.COMPLETED)
    }

    private suspend fun getCallsWithStatus(callStatus: CallStatus): List<SavedCall> {
        return callDao.getCallsWithStatus(statusConverter.convert(callStatus)).map { call ->
            SavedCall(
                id = call.id,
                name = call.contactName,
                phone = call.contactPhone,
                photoUrl = call.photoUrl,
                date = Date(call.date)
            )
        }
    }

    companion object {
        private val statusConverter = CallStatusToStringConverter()
    }
}
