package com.alenskaya.fakecalls.domain.calls.usecase

import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.calls.repository.CallsRepository
import kotlinx.coroutines.flow.flow

class CreateCallUseCase(
    private val callsRepository: CallsRepository,
) {
    operator fun invoke(createNewCallRequest: CreateNewCallRequest) = flow {
        emit(callsRepository.createCall(createNewCallRequest))
    }
}
