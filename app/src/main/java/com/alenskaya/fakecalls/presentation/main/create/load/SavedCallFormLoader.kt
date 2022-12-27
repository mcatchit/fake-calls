package com.alenskaya.fakecalls.presentation.main.create.load

import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.calls.GetCallByIdUseCase
import com.alenskaya.fakecalls.presentation.main.create.converter.SavedCallToFormModelConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Loads initial form data for saved calls.
 * @property callId - call identifier.
 * @property showDate - whether to display date or not.
 */
class SavedCallFormLoader(
    private val callId: Int,
    private val showDate: Boolean,
    private val coroutineScope: CoroutineScope,
    private val getCallByIdUseCase: GetCallByIdUseCase
) : InitialFormLoader {

    override fun load(loadInitialDataCallback: LoadInitialDataCallback) {
        coroutineScope.launch(Dispatchers.IO) {
            getCallByIdUseCase(callId)
                .collect { response ->
                    when (response) {
                        is BaseResponse.Success -> {
                            loadInitialDataCallback.success(
                                SavedCallToFormModelConverter(showDate).convert(response.payload)
                            )
                        }
                        is BaseResponse.Error -> loadInitialDataCallback.error()
                    }
                }
        }
    }
}
