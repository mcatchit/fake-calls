package com.alenskaya.fakecalls.presentation.main.create.load

import com.alenskaya.fakecalls.domain.calls.GetCallByIdUseCase
import com.alenskaya.fakecalls.domain.contacts.GetFakeContactUseCase
import com.alenskaya.fakecalls.presentation.main.create.CreateCallScreenMode
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * Creates Initial form loader for specified mode.
 */
class InitialDataLoaderFactory @Inject constructor(
    private val getFakeContactUseCase: GetFakeContactUseCase,
    private val getCallByIdUseCase: GetCallByIdUseCase,
) {

    fun create(
        mode: CreateCallScreenMode,
        coroutineScope: CoroutineScope
    ): InitialFormLoader {
        return when (mode) {
            is CreateCallScreenMode.CreateCustom -> CustomContactFormLoader()
            is CreateCallScreenMode.CreateFake -> SuggestedContactFormLoader(
                mode.id,
                coroutineScope,
                getFakeContactUseCase
            )
            is CreateCallScreenMode.Edit -> SavedCallFormLoader(
                mode.callId,
                true,
                coroutineScope,
                getCallByIdUseCase
            )
            is CreateCallScreenMode.Repeat -> SavedCallFormLoader(
                mode.callId,
                false,
                coroutineScope,
                getCallByIdUseCase
            )
        }
    }
}
