package com.alenskaya.fakecalls.domain.calls

import javax.inject.Inject

/**
 * Requests list of saved calls
 */
class LoadSavedCallsUseCase @Inject constructor(
    private val callsRepository: CallsRepository
) {
    operator fun invoke() = callsRepository.getSavedCalls()
}
