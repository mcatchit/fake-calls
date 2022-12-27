package com.alenskaya.fakecalls.presentation.main.create.load

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.contacts.GetFakeContactUseCase
import com.alenskaya.fakecalls.presentation.main.create.converter.SavedFakeContactToFormConverter
import com.alenskaya.fakecalls.presentation.main.create.model.CreateCallScreenFormModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Loads initial form for creating call from suggested contacts.
 */
class SuggestedContactFormLoader(
    private val suggestedContactId: Int,
    private val coroutineScope: CoroutineScope,
    private val getFakeContactUseCase: GetFakeContactUseCase
) : InitialFormLoader {

    override fun load(loadInitialDataCallback: LoadInitialDataCallback) {
        coroutineScope.launch(Dispatchers.IO) {
            getFakeContactUseCase(suggestedContactId)
                .map { response ->
                    when (response) {
                        is BaseResponse.Success -> SavedFakeContactToFormConverter.convert(response.payload)
                        is BaseResponse.Error -> CreateCallScreenFormModel.initial()
                    }
                }
                .collect { formModel ->
                    loadInitialDataCallback.success(formModel)
                }
        }
    }
}
