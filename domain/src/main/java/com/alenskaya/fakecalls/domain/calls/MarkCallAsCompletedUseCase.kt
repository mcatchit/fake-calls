package com.alenskaya.fakecalls.domain.calls

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Executes request to [callsRepository] to mark call as completed.
 */
class MarkCallAsCompletedUseCase @Inject constructor(
    private val callsRepository: CallsRepository
) {
    /**
     * Invokes useCase.
     * @param callId - id of updating call.
     */
    operator fun invoke(callId: Int) = flow {
        emit(callsRepository.markCallAsCompleted(callId))
    }
}
