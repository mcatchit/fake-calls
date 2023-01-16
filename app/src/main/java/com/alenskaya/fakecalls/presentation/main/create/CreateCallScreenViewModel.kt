package com.alenskaya.fakecalls.presentation.main.create

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.alenskaya.fakecalls.presentation.DialogsDisplayer
import com.alenskaya.fakecalls.presentation.NotificationPermissionManager
import com.alenskaya.fakecalls.presentation.NotificationPermissionCallback
import com.alenskaya.fakecalls.presentation.main.create.converter.CreateCallScreenModeToLabelsConverter
import com.alenskaya.fakecalls.presentation.main.create.model.CreateCallScreenFormModel
import com.alenskaya.fakecalls.presentation.execution.CallsScheduler
import com.alenskaya.fakecalls.presentation.firebase.FeatureFlags
import com.alenskaya.fakecalls.presentation.main.create.load.InitialFormLoader
import com.alenskaya.fakecalls.presentation.main.create.load.InitialDataLoaderFactory
import com.alenskaya.fakecalls.presentation.main.create.load.LoadInitialDataCallback
import com.alenskaya.fakecalls.presentation.main.create.model.CreateNewCallData
import com.alenskaya.fakecalls.presentation.main.create.model.FakeCallPermission
import com.alenskaya.fakecalls.presentation.main.create.submit.FormSubmitter
import com.alenskaya.fakecalls.presentation.main.create.submit.SubmitCallback
import com.alenskaya.fakecalls.presentation.main.create.submit.SubmitterFactory
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
import com.alenskaya.fakecalls.presentation.navigation.NavigateBack
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Create call screen view model.
 * @property imageLoader - application image loader.
 * @property createStrings - string resources for create screen.
 * @property dialogsDisplayer - displays dialogs.
 * @property initialDataLoaderFactory - creates initial data loader for specified mode.
 * @property submitterFactory - creates submitter for specified mode.
 * @property callsScheduler - schedules calls.
 * @property notificationPermissionManager - provides information about notification permission.
 * @property applicationRouter - global application router.
 */
@HiltViewModel
class CreateCallScreenViewModel @Inject constructor(
    firebaseRemoteConfig: FirebaseRemoteConfig,
    val imageLoader: ImageLoader,
    val createStrings: CreateStrings,
    val dialogsDisplayer: DialogsDisplayer,
    private val initialDataLoaderFactory: InitialDataLoaderFactory,
    private val submitterFactory: SubmitterFactory,
    private val callsScheduler: CallsScheduler,
    private val notificationPermissionManager: NotificationPermissionManager,
    private val applicationRouter: ApplicationRouter
) : ViewModel() {

    val screenState: StateFlow<CreateCallScreenState>
        get() = reducer.state

    val oneTimeEffect: SharedFlow<CreateCallScreenOneTimeUiEffect>
        get() = reducer.oneTimeEffect

    private var initialFormLoader by Delegates.notNull<InitialFormLoader>()
    private var submitter by Delegates.notNull<FormSubmitter>()

    private val reducer =
        CreateCallScreenStateReducer(
            viewModelScope = viewModelScope,
            initialState = CreateCallScreenState.initial(
                firebaseRemoteConfig.getBoolean(FeatureFlags.IS_CREATE_BUTTON_GREEN)
            ),
            createStrings = createStrings,
            navigateBackCallback = ::navigateBack,
            submitFormCallBack = ::submitForm
        )

    /**
     * Must be called before screen initialization.
     */
    fun setMode(mode: CreateCallScreenMode) {
        init(mode)

        sendEvent(
            CreateCallScreenEvent.ModeLoaded(
                CreateCallScreenModeToLabelsConverter(createStrings).convert(mode)
            )
        )

        loadInitialData()
    }

    /**
     * Sends ui event.
     */
    fun sendEvent(event: CreateCallScreenEvent) {
        reducer.sendEvent(event)
    }

    private fun init(mode: CreateCallScreenMode) {
        initialFormLoader = initialDataLoaderFactory.create(mode, viewModelScope)
        submitter = submitterFactory.create(mode, viewModelScope)
    }

    private fun loadInitialData() {
        sendEvent(CreateCallScreenEvent.FormLoading)
        initialFormLoader.load(getLoadInitialDataCallback())
    }

    private fun navigateBack() {
        applicationRouter.navigate(NavigateBack)
    }

    private fun submitForm(createNewCallData: CreateNewCallData) {
        checkNotificationPermissionAndDo {
            checkAlarmManagerPermissionAndDo {
                sendEvent(CreateCallScreenEvent.ProcessingSubmit)

                submitter.submit(createNewCallData, getSubmitCallback())
            }
        }
    }

    private fun getLoadInitialDataCallback() = object : LoadInitialDataCallback {

        override fun success(data: CreateCallScreenFormModel) {
            sendEvent(CreateCallScreenEvent.FormLoaded(data))
        }

        override fun error() {
            sendEvent(CreateCallScreenEvent.CannotLoadInitialData(createStrings.failedToLoadPrefilledData()))
        }
    }

    @SuppressLint("InlinedApi")
    private fun checkNotificationPermissionAndDo(actionToDoIfPermissionGranted: () -> Unit) {
        notificationPermissionManager.requestNotificationPermission(
            NotificationPermissionCallback(
                doWhenGranted = actionToDoIfPermissionGranted,
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

    private fun getSubmitCallback() = object : SubmitCallback {
        override fun success() {
            navigateBack()
        }

        override fun error() {
            sendEvent(CreateCallScreenEvent.UnsuccessfulSubmit)
        }
    }
}
