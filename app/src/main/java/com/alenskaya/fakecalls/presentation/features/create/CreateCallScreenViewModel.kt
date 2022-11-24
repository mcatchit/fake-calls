package com.alenskaya.fakecalls.presentation.features.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.alenskaya.fakecalls.domain.calls.CreateCallUseCase
import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.presentation.DialogsDisplayer
import com.alenskaya.fakecalls.presentation.features.create.model.CreateCallScreenFormModel
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
import com.alenskaya.fakecalls.presentation.navigation.NavigateBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Create call screen view model.
 * @property imageLoader - application image loader.
 * @property applicationRouter - global application router.
 * @property createCallUseCase - use case to create a new call.
 */
@HiltViewModel
class CreateCallScreenViewModel @Inject constructor(
    val imageLoader: ImageLoader,
    val applicationRouter: ApplicationRouter,
    val dialogsDisplayer: DialogsDisplayer,
    val createCallUseCase: CreateCallUseCase
) : ViewModel() {

    private val reducer =
        CreateCallScreenStateReducer(
            viewModelScope = viewModelScope,
            initialState = CreateCallScreenState.initial(),
            navigateBackCallback = ::navigateBack,
            submitFormCallBack = ::submitForm
        )

    val screenState: StateFlow<CreateCallScreenState>
        get() = reducer.state

    val oneTimeEffect: SharedFlow<CreateCallScreenOneTimeUiEffect>
        get() = reducer.oneTimeEffect

    fun sendEvent(event: CreateCallScreenEvent) {
        reducer.sendEvent(event)
    }

    private fun navigateBack() {
        applicationRouter.navigate(NavigateBack)
    }

    private fun submitForm(form: CreateCallScreenFormModel) {
        sendEvent(CreateCallScreenEvent.ProcessingSubmit)

        viewModelScope.launch(Dispatchers.IO) {
            createCallUseCase(form.toCreateNewCallRequest())

            withContext(Dispatchers.Main) {
                navigateBack() //TODO finish with result
            }
        }
    }

    private fun CreateCallScreenFormModel.toCreateNewCallRequest() = CreateNewCallRequest(
        name = name ?: error("Cannot be null. Should be called after validation"),
        phone = phone ?: error("Cannot be null. Should be called after validation"),
        date = date ?: error("Cannot be null. Should be called after validation"),
        photoUrl = photo
    )
}
