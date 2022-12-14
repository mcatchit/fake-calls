package com.alenskaya.fakecalls.domain.calls

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Requests call with specific id.
 */
class GetCallByIdUseCase @Inject constructor(
    private val callsRepository: CallsRepository
) {
    operator fun invoke(callId: Int) = flow {
        emit(callsRepository.getSavedCallById(callId))
    }
}
