package com.alenskaya.fakecalls.presentation.main.create.submit

import com.alenskaya.fakecalls.presentation.main.create.model.CreateNewCallData

/**
 * Executes form submit.
 */
interface FormSubmitter {
    fun submit(createNewCallData: CreateNewCallData, submitCallback: SubmitCallback)
}

/**
 * Passes result of submit.
 */
interface SubmitCallback {
    fun success()
    fun error()
}
