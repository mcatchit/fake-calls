package com.alenskaya.fakecalls.presentation.features.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.calls.CreateCallUseCase
import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.contacts.GetFakeContactUseCase
import com.alenskaya.fakecalls.presentation.DialogsDisplayer
import com.alenskaya.fakecalls.presentation.CallsDataChangedNotifier
import com.alenskaya.fakecalls.presentation.features.create.converter.CreateCallScreenModeToLabelsConverter
import com.alenskaya.fakecalls.presentation.features.create.converter.SavedFakeContactToFormConverter
import com.alenskaya.fakecalls.presentation.features.create.model.CreateCallScreenFormModel
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
import com.alenskaya.fakecalls.presentation.navigation.NavigateBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
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
    val dialogsDisplayer: DialogsDisplayer,
    private val applicationRouter: ApplicationRouter,
    private val callsDataChangedNotifier: CallsDataChangedNotifier,
    private val createCallUseCase: CreateCallUseCase,
    private val getFakeContactUseCase: GetFakeContactUseCase,
) : ViewModel() {

    val screenState: StateFlow<CreateCallScreenState>
        get() = reducer.state

    val oneTimeEffect: SharedFlow<CreateCallScreenOneTimeUiEffect>
        get() = reducer.oneTimeEffect

    private lateinit var mode: CreateCallScreenMode

    private val reducer =
        CreateCallScreenStateReducer(
            viewModelScope = viewModelScope,
            initialState = CreateCallScreenState.initial(),
            navigateBackCallback = ::navigateBack,
            submitFormCallBack = ::submitForm
        )

    /**
     * Must be called before screen initialization
     */
    fun setMode(mode: CreateCallScreenMode) {
        this.mode = mode
        sendEvent(
            CreateCallScreenEvent.ModeLoaded(
                labels = CreateCallScreenModeToLabelsConverter().convert(mode)
            )
        )
        loadFormInfoIfNecessary(mode)
    }

    /**
     * Sends ui event
     */
    fun sendEvent(event: CreateCallScreenEvent) {
        reducer.sendEvent(event)
    }

    private fun loadFormInfoIfNecessary(mode: CreateCallScreenMode) {
        if (mode is CreateCallScreenMode.CreateFake) {
            loadChosenSuggestedContact(mode.id)
        }
    }

    private fun loadChosenSuggestedContact(suggestedContactId: Int) {
        sendEvent(CreateCallScreenEvent.FormLoading)

        viewModelScope.launch(Dispatchers.IO) {
            getFakeContactUseCase(suggestedContactId)
                .map { response ->
                    when (response) {
                        is BaseResponse.Success -> SavedFakeContactToFormConverter.convert(response.payload)
                        is BaseResponse.Error -> CreateCallScreenFormModel.initial()
                    }
                }
                .collect { formModel ->
                    sendEvent(CreateCallScreenEvent.FormLoaded(formModel))
                }
        }
    }

    private fun navigateBack() {
        applicationRouter.navigate(NavigateBack)
    }

    private fun submitForm(form: CreateCallScreenFormModel) {
        sendEvent(CreateCallScreenEvent.ProcessingSubmit)

        viewModelScope.launch(Dispatchers.IO) {
            createCallUseCase(form.toCreateNewCallRequest()).collect()

            withContext(Dispatchers.Main) {
                callsDataChangedNotifier.callsDataChanged()
                navigateBack()
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
