package com.alenskaya.fakecalls.presentation.main.create.submit

import com.alenskaya.fakecalls.domain.calls.CreateCallUseCase
import com.alenskaya.fakecalls.domain.calls.DeleteCallUseCase
import com.alenskaya.fakecalls.domain.calls.GetCallByIdUseCase
import com.alenskaya.fakecalls.presentation.execution.CallsScheduler
import com.alenskaya.fakecalls.presentation.main.create.CreateCallScreenMode
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * Creates submitter according to specified mode.
 */
class SubmitterFactory @Inject constructor(
    private val callsScheduler: CallsScheduler,
    private val createCallUseCase: CreateCallUseCase,
    private val getCallByIdUseCase: GetCallByIdUseCase,
    private val deleteCallUseCase: DeleteCallUseCase,
    private val firebaseAnalytics: FirebaseAnalytics
) {

    fun create(
        mode: CreateCallScreenMode,
        coroutineScope: CoroutineScope
    ): FormSubmitter {
        return when (mode) {
            is CreateCallScreenMode.Edit -> EditCallSubmitter(
                coroutineScope,
                callsScheduler,
                createCallUseCase,
                mode.callId,
                getCallByIdUseCase,
                deleteCallUseCase
            )
            else -> SaveAndSendFirebaseAnalyticsSubmitter(
                coroutineScope,
                callsScheduler,
                createCallUseCase,
                firebaseAnalytics
            )
        }
    }
}
