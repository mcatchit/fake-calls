package com.alenskaya.fakecalls.data.local.db

import com.alenskaya.fakecalls.data.local.catchDatabaseExceptions
import com.alenskaya.fakecalls.data.local.catchExceptions
import com.alenskaya.fakecalls.data.local.db.calls.CallDao
import com.alenskaya.fakecalls.data.local.db.calls.CallEntity
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Calls repository implementation based on the Room database
 */
internal class CallsRoomRepository @Inject constructor(
    private val callDao: CallDao
) : CallsRepository {

    override suspend fun createCall(createNewCallRequest: CreateNewCallRequest): BaseResponse<SavedCall, DatabaseError> {
        return catchExceptions {
            val newCallId = callDao.createNewCall(createNewCallRequest.toCallEntity()).toInt()
            BaseResponse.Success(callDao.getCallById(newCallId).toSavedCall())
        }
    }

    override fun getSavedCalls(): Flow<BaseResponse<List<SavedCall>, DatabaseError>> {
        return callDao.getAllCalls()
            .map<List<CallEntity>, BaseResponse<List<SavedCall>, DatabaseError>> { callEntities ->
                BaseResponse.Success(
                    callEntities.map { callEntity ->
                        callEntity.toSavedCall()
                    }
                )
            }
            .catchDatabaseExceptions()
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

    override suspend fun deleteCall(callId: Int): BaseResponse<Unit, DatabaseError> {
        return catchExceptions {
            BaseResponse.Success(callDao.deleteCall(callId))
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
