package com.alenskaya.fakecalls.domain.calls

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Deletes call with specific id.
 */
class DeleteCallUseCase @Inject constructor(
    private val callsRepository: CallsRepository
) {
    operator fun invoke(callId: Int) = flow {
        emit(callsRepository.deleteCall(callId))
    }
}
