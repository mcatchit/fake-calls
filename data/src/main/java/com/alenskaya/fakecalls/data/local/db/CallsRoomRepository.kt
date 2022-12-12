package com.alenskaya.fakecalls.data.local.db

import com.alenskaya.fakecalls.data.local.catchExceptions
import com.alenskaya.fakecalls.data.local.db.calls.CallDao
import com.alenskaya.fakecalls.data.local.db.calls.converter.CallStatusToStringConverter
import com.alenskaya.fakecalls.data.local.db.calls.converter.toCallEntity
import com.alenskaya.fakecalls.data.local.db.calls.converter.toSavedCall
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.calls.model.CallStatus
import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import com.alenskaya.fakecalls.domain.calls.CallsRepository
import com.alenskaya.fakecalls.domain.calls.model.UpdateCallRequest
import javax.inject.Inject

/**
 * Calls repository implementation based on the Room database
 */
internal class CallsRoomRepository @Inject constructor(
    private val callDao: CallDao
) : CallsRepository {

    override suspend fun createCall(createNewCallRequest: CreateNewCallRequest): BaseResponse<Int, DatabaseError> {
        return catchExceptions {
            BaseResponse.Success(callDao.createNewCall(createNewCallRequest.toCallEntity()).toInt())
        }
    }

    override suspend fun getSavedCalls(): BaseResponse<List<SavedCall>, DatabaseError> {
        return catchExceptions {
            BaseResponse.Success(callDao.getAllCalls().map { callEntity ->
                callEntity.toSavedCall()
            }.sortedBy { savedCall ->
                savedCall.date
            })
        }
    }

    override suspend fun getSavedCallById(callId: Int): BaseResponse<SavedCall, DatabaseError> {
        return catchExceptions {
            BaseResponse.Success(callDao.getCallById(callId).toSavedCall())
        }
    }

    override suspend fun updateCall(updateCallRequest: UpdateCallRequest): BaseResponse<Unit, DatabaseError> {
        return catchExceptions {
            BaseResponse.Success(callDao.updateCall(updateCallRequest.toCallEntity()))
        }
    }

    override suspend fun markCallAsCompleted(callId: Int): BaseResponse<Unit, DatabaseError> {
        return catchExceptions {
            val call = callDao.getCallById(callId)

            BaseResponse.Success(
                callDao.updateCall(
                    call.copy(status = CallStatusToStringConverter.convert(CallStatus.COMPLETED))
                )
            )
        }
    }
}
