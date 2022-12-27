package com.alenskaya.fakecalls.presentation.main.create.load

import com.alenskaya.fakecalls.presentation.main.create.model.CreateCallScreenFormModel

/**
 * Loads initial create call form.
 */
interface InitialFormLoader {
    fun load(loadInitialDataCallback: LoadInitialDataCallback)
}

/**
 * Passes results of initial data loading.
 */
interface LoadInitialDataCallback {
    fun success(data: CreateCallScreenFormModel)
    fun error()
}
