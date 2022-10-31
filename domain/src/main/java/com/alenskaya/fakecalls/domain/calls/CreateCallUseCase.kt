package com.alenskaya.fakecalls.domain.calls

import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.calls.CallsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Requests creating of a new call
 */
class CreateCallUseCase @Inject constructor(
    private val callsRepository: CallsRepository,
) {
    operator fun invoke(createNewCallRequest: CreateNewCallRequest) = flow {
        emit(callsRepository.createCall(createNewCallRequest))
    }
}
