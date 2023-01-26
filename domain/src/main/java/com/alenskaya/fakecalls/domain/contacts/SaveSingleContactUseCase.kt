package com.alenskaya.fakecalls.domain.contacts

import com.alenskaya.fakecalls.domain.contacts.model.ContactToSave
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Locally saves passed contact.
 */
class SaveSingleContactUseCase @Inject constructor(
    private val localRepository: ContactsLocalRepository
) {
    suspend operator fun invoke(contactToSave: ContactToSave) = flow {
        emit(localRepository.saveContact(contactToSave))
    }
}
