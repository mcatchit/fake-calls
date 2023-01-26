package com.alenskaya.fakecalls.domain.contacts

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.DatabaseError
import com.alenskaya.fakecalls.domain.contacts.model.SavedContact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Requests fake contact with provided id
 */
class GetFakeContactUseCase @Inject constructor(
    private val localRepository: ContactsLocalRepository
) {

    suspend operator fun invoke(fakeContactId: Int): Flow<BaseResponse<SavedContact, DatabaseError>> =
        flow {
            emit(localRepository.getSavedContact(fakeContactId))
        }
}
