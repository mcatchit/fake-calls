package com.alenskaya.fakecalls.domain.calls

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.calls.model.CallStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

//todo
class RefreshCallsStatusesUseCase @Inject constructor(
    private val callsRepository: CallsRepository
) {
    operator fun invoke(): Flow<Unit> = flow {
        val allCalls = callsRepository.getSavedCalls()
        if (allCalls is BaseResponse.Success) {
            val currentDate = Date()
            allCalls.payload.forEach { call ->
                if (call.callStatus != CallStatus.COMPLETED && call.date < currentDate) {
                    callsRepository.markCallAsCompleted(call.id)
                }
            }
        }
    }
}
