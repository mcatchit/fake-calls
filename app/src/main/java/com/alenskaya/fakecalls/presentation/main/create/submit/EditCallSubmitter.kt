package com.alenskaya.fakecalls.presentation.main.create.submit

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.calls.CreateCallUseCase
import com.alenskaya.fakecalls.domain.calls.DeleteCallUseCase
import com.alenskaya.fakecalls.domain.calls.GetCallByIdUseCase
import com.alenskaya.fakecalls.domain.calls.model.SavedCall
import com.alenskaya.fakecalls.presentation.execution.CallsScheduler
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.main.create.model.CreateNewCallData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Submitter which executes update of the call : deletes an old call and saves a new one.
 */
class EditCallSubmitter(
    coroutineScope: CoroutineScope,
    callsScheduler: CallsScheduler,
    createCallUseCase: CreateCallUseCase,
    private val editableCallId: Int,
    private val getCallByIdUseCase: GetCallByIdUseCase,
    private val deleteCallUseCase: DeleteCallUseCase
) : SaveNewCallSubmitter(coroutineScope, callsScheduler, createCallUseCase) {

    override fun submit(createNewCallData: CreateNewCallData, submitCallback: SubmitCallback) {
        loadCall(
            doOnSuccess = { call ->
                updateCall(call, createNewCallData, submitCallback)
            },
            doOnError = {
                submitCallback.error()
            }
        )
    }

    private fun updateCall(
        editableCall: SavedCall,
        createNewCallData: CreateNewCallData,
        submitCallback: SubmitCallback
    ) {
        deleteCall(
            savedCall = editableCall,
            onSuccess = {
                saveCall(
                    createNewCallData = createNewCallData,
                    doWhenCallSaved = {},
                    submitCallback = submitCallback
                )
            },
            onError = { submitCallback.error() }
        )
    }

    private fun loadCall(doOnSuccess: (SavedCall) -> Unit, doOnError: () -> Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            getCallByIdUseCase(editableCallId)
                .collect { response ->
                    when (response) {
                        is BaseResponse.Success -> {
                            doOnSuccess(response.payload)
                        }
                        is BaseResponse.Error -> doOnError()
                    }
                }
        }
    }

    private fun deleteCall(savedCall: SavedCall, onSuccess: () -> Unit, onError: () -> Unit) {
        coroutineScope.launch(Dispatchers.Main) {
            callsScheduler.cancelCall(savedCall.toCallExecutionParams())

            withContext(Dispatchers.IO) {
                deleteCallUseCase(savedCall.id).collect { response ->
                    when (response) {
                        is BaseResponse.Success -> onSuccess()
                        is BaseResponse.Error -> {
                            onError()
                        }
                    }
                }
            }
        }
    }

    private fun SavedCall.toCallExecutionParams() = CallExecutionParams(
        callId = id,
        name = name,
        phone = phone,
        photoUrl = photoUrl,
        requestCode = requestCode
    )
}
