package com.alenskaya.fakecalls.data.local

import com.alenskaya.fakecalls.data.local.db.CallDao
import com.alenskaya.fakecalls.data.local.db.CallEntity
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.calls.model.CallStatus
import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import com.alenskaya.fakecalls.domain.calls.CallsRepository
import java.util.Date
import javax.inject.Inject

/**
 * Calls repository implementation based on the Room database
 */
internal class CallsRoomRepository @Inject constructor(
    private val callDao: CallDao
) : CallsRepository {

    override suspend fun createCall(createNewCallRequest: CreateNewCallRequest): BaseResponse<Unit, DatabaseError> {
        return catchExceptions {
            BaseResponse.Success(
                callDao.createNewCall(
                    CallEntity(
                        contactName = createNewCallRequest.name,
                        contactPhone = createNewCallRequest.phone,
                        date = createNewCallRequest.date.time,
                        photoUrl = createNewCallRequest.photoUrl,
                        status = statusConverter.convert(CallStatus.SCHEDULED)
                    )
                )
            )
        }
    }

    override suspend fun getSavedCalls(): BaseResponse<List<SavedCall>, DatabaseError> {
        return catchExceptions {
            BaseResponse.Success(callDao.getAllCalls().map { callEntity ->
                SavedCall(
                    id = callEntity.id,
                    name = callEntity.contactName,
                    phone = callEntity.contactPhone,
                    photoUrl = callEntity.photoUrl,
                    date = Date(callEntity.date),
                    callStatus = statusConverter.convertBack(callEntity.status)
                )
            }.sortedBy { savedCall ->
                savedCall.date
            })
        }
    }

    companion object {
        private val statusConverter = CallStatusToStringConverter()
    }
}
