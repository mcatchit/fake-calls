package com.alenskaya.fakecalls.presentation.main.create.submit

import com.alenskaya.fakecalls.domain.calls.CreateCallUseCase
import com.alenskaya.fakecalls.presentation.execution.CallsScheduler
import com.alenskaya.fakecalls.presentation.firebase.AnalyticsEvents
import com.alenskaya.fakecalls.presentation.main.create.model.CreateNewCallData
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.CoroutineScope

/**
 * Submit which saves new call and sends firebase event.
 */
class SaveAndSendFirebaseAnalyticsSubmitter(
    coroutineScope: CoroutineScope,
    callsScheduler: CallsScheduler,
    createCallUseCase: CreateCallUseCase,
    private val firebaseAnalytics: FirebaseAnalytics
) : SaveNewCallSubmitter(coroutineScope, callsScheduler, createCallUseCase) {

    override fun submit(createNewCallData: CreateNewCallData, submitCallback: SubmitCallback) {
        saveCall(
            createNewCallData = createNewCallData,
            doWhenCallSaved = { sendFirebaseAnalytics() },
            submitCallback = submitCallback
        )
    }

    private fun sendFirebaseAnalytics() {
        firebaseAnalytics.logEvent(AnalyticsEvents.CALL_CREATED, null)
    }
}
