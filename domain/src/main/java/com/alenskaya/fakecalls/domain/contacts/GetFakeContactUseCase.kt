package com.alenskaya.fakecalls.domain.contacts

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.contacts.model.SavedFakeContact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Requests fake contact with provided id
 */
class GetFakeContactUseCase @Inject constructor(
    private val localRepository: FakeContactLocalRepository
) {

    suspend operator fun invoke(fakeContactId: Int): Flow<BaseResponse<SavedFakeContact, DatabaseError>> =
        flow {
            emit(localRepository.getFakeContact(fakeContactId))
        }
}
