package com.alenskaya.fakecalls.data.local

import com.alenskaya.fakecalls.data.local.db.CallDao
import com.alenskaya.fakecalls.data.local.db.CallEntity
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.calls.model.CallStatus
import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import com.alenskaya.fakecalls.domain.calls.repository.CallsRepository
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


    override suspend fun getPlannedCalls(): BaseResponse<List<SavedCall>, DatabaseError> {
        return catchExceptions {
            BaseResponse.Success(getCallsWithStatus(CallStatus.SCHEDULED))
        }
    }

    override suspend fun getCompletedCalls(): BaseResponse<List<SavedCall>, DatabaseError> {
        return catchExceptions {
            BaseResponse.Success(getCallsWithStatus(CallStatus.COMPLETED))
        }
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
