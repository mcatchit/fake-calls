package com.alenskaya.fakecalls.presentation.main.create

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.alenskaya.fakecalls.domain.BaseResponse
import com.alenskaya.fakecalls.domain.calls.CreateCallUseCase
import com.alenskaya.fakecalls.domain.calls.model.CreateNewCallRequest
import com.alenskaya.fakecalls.domain.contacts.GetFakeContactUseCase
import com.alenskaya.fakecalls.presentation.DialogsDisplayer
import com.alenskaya.fakecalls.presentation.CallsDataChangedNotifier
import com.alenskaya.fakecalls.presentation.NotificationPermissionManager
import com.alenskaya.fakecalls.presentation.NotificationPermissionCallback
import com.alenskaya.fakecalls.presentation.main.create.converter.CreateCallScreenModeToLabelsConverter
import com.alenskaya.fakecalls.presentation.main.create.converter.SavedFakeContactToFormConverter
import com.alenskaya.fakecalls.presentation.main.create.model.CreateCallScreenFormModel
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.execution.CallsScheduler
import com.alenskaya.fakecalls.presentation.firebase.AnalyticsEvents
import com.alenskaya.fakecalls.presentation.firebase.FeatureFlags
import com.alenskaya.fakecalls.presentation.main.create.model.FakeCallPermission
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
import com.alenskaya.fakecalls.presentation.navigation.NavigateBack
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

/**
 * Create call screen view model.
 * @property imageLoader - application image loader.
 * @property applicationRouter - global application router.
 * @property createCallUseCase - use case to create a new call.
 */
@HiltViewModel
class CreateCallScreenViewModel @Inject constructor(
    firebaseRemoteConfig: FirebaseRemoteConfig,
    val imageLoader: ImageLoader,
    val dialogsDisplayer: DialogsDisplayer,
    private val callsScheduler: CallsScheduler,
    private val notificationPermissionManager: NotificationPermissionManager,
    private val applicationRouter: ApplicationRouter,
    private val callsDataChangedNotifier: CallsDataChangedNotifier,
    private val createCallUseCase: CreateCallUseCase,
    private val getFakeContactUseCase: GetFakeContactUseCase,
    private val firebaseAnalytics: FirebaseAnalytics
) : ViewModel() {

    val screenState: StateFlow<CreateCallScreenState>
        get() = reducer.state

    val oneTimeEffect: SharedFlow<CreateCallScreenOneTimeUiEffect>
        get() = reducer.oneTimeEffect

    private lateinit var mode: CreateCallScreenMode

    private val reducer =
        CreateCallScreenStateReducer(
            viewModelScope = viewModelScope,
            initialState = CreateCallScreenState.initial(
                firebaseRemoteConfig.getBoolean(
                    FeatureFlags.IS_CREATE_BUTTON_GREEN
                )
            ),
            navigateBackCallback = ::navigateBack,
            submitFormCallBack = ::submitForm
        )

    /**
     * Must be called before screen initialization.
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
     * Sends ui event.
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
        //todo refactoring
        checkNotificationPermission {
            checkAlarmManagerPermissionAndDo {
                sendEvent(CreateCallScreenEvent.ProcessingSubmit)

                viewModelScope.launch(Dispatchers.IO) {
                    createCallUseCase(form.toCreateNewCallRequest()).collect { response ->
                        withContext(Dispatchers.Main) {
                            if (response is BaseResponse.Success) {
                                scheduleCall(
                                    form.toCallExecutionParams(response.payload),
                                    form.date ?: error("Date cannot be null after validation")
                                )
                                logCallCreatedEvent()
                            } else {
                                sendEvent(CreateCallScreenEvent.UnsuccessfulSubmit)
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("InlinedApi")
    private fun checkNotificationPermission(action: () -> Unit) {
        notificationPermissionManager.requestNotificationPermission(
            NotificationPermissionCallback(
                doWhenGranted = action,
                doWhenNotGranted = {
                    sendEvent(CreateCallScreenEvent.PermissionNotGranted(FakeCallPermission.SHOW_NOTIFICATION))
                }
            )
        )
    }

    private fun checkAlarmManagerPermissionAndDo(actionToDoIfPermissionGranted: () -> Unit) {
        if (callsScheduler.hasPermissionToScheduleACall()) {
            actionToDoIfPermissionGranted()
        } else {
            sendEvent(CreateCallScreenEvent.PermissionNotGranted(FakeCallPermission.SCHEDULE_ALARM))
        }
    }

    private fun CreateCallScreenFormModel.toCreateNewCallRequest() = CreateNewCallRequest(
        name = name ?: error("Cannot be null. Should be called after validation"),
        phone = phone ?: error("Cannot be null. Should be called after validation"),
        date = date ?: error("Cannot be null. Should be called after validation"),
        photoUrl = photo
    )

    private fun scheduleCall(callExecutionParams: CallExecutionParams, whenExecute: Date) {
        callsScheduler.scheduleCall(callExecutionParams, whenExecute)

        callsDataChangedNotifier.callsDataChanged()
        navigateBack()
    }

    private fun CreateCallScreenFormModel.toCallExecutionParams(callId: Int) = CallExecutionParams(
        callId = callId,
        name = name ?: error("Cannot be null. Should be called after validation"),
        phone = phone ?: error("Cannot be null. Should be called after validation"),
        photoUrl = photo
    )

    private fun logCallCreatedEvent() {
        firebaseAnalytics.logEvent(AnalyticsEvents.CALL_CREATED, null)
    }
}
