package com.alenskaya.fakecalls.presentation.main.create.submit

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.calls.CreateCallUseCase
import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import com.alenskaya.fakecalls.presentation.execution.CallsScheduler
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.main.create.model.CreateNewCallData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.UUID

/**
 * General save submit.
 */
abstract class SaveNewCallSubmitter(
    protected val coroutineScope: CoroutineScope,
    protected val callsScheduler: CallsScheduler,
    private val createCallUseCase: CreateCallUseCase
) : FormSubmitter {

    /**
     * Saves and schedules call.
     * @param createNewCallData - data of new call.
     * @param doWhenCallSaved - called when call successfully saved.
     */
    protected fun saveCall(
        createNewCallData: CreateNewCallData,
        doWhenCallSaved: () -> Unit,
        submitCallback: SubmitCallback
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            createCallUseCase(createNewCallData.toCreateNewCallRequest()).collect { response ->

                withContext(Dispatchers.Main) {
                    if (response is BaseResponse.Success) {
                        with(response.payload) {
                            scheduleCall(toCallExecutionParams(), date)
                        }
                        submitCallback.success()

                        doWhenCallSaved()
                    } else {
                        submitCallback.error()
                    }
                }
            }
        }
    }

    private fun CreateNewCallData.toCreateNewCallRequest() = CreateNewCallRequest(
        name = name,
        phone = phone,
        date = date,
        photoUrl = photoUrl,
        requestCode = UUID.randomUUID().hashCode()
    )

    private fun scheduleCall(callExecutionParams: CallExecutionParams, whenExecute: Date) {
        callsScheduler.scheduleCall(callExecutionParams, whenExecute)
    }

    private fun SavedCall.toCallExecutionParams() = CallExecutionParams(
        callId = id,
        name = name,
        phone = phone,
        photoUrl = photoUrl,
        requestCode = requestCode
    )
}
