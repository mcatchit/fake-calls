package com.alenskaya.fakecalls.presentation.main.create.load

import com.alenskaya.fakecalls.presentation.main.create.model.CreateCallScreenFormModel

/**
 * Loads initial data for create custom call.
 */
class CustomContactFormLoader : InitialFormLoader {

    override fun load(loadInitialDataCallback: LoadInitialDataCallback) {
        loadInitialDataCallback.success(CreateCallScreenFormModel.initial())
    }
}
