package com.alenskaya.fakecalls.domain.calls.usecase

import com.alenskaya.fakecalls.domain.calls.repository.CallsRepository
import kotlinx.coroutines.flow.flow

class LoadPlannedCallsUseCase(
    private val callsRepository: CallsRepository
) {
    operator fun invoke() = flow {
        emit(callsRepository.getPlannedCalls())
    }
}
