package com.alenskaya.fakecalls.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.execution.CallsScheduler
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
import com.alenskaya.fakecalls.presentation.theme.FakeCallsTheme
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Root app activity.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var callsScheduler: CallsScheduler

    @Inject
    lateinit var applicationRouter: ApplicationRouter

    @Inject
    lateinit var dialogsDisplayer: DialogsDisplayer

    @Inject
    lateinit var notificationPermissionManager: NotificationPermissionManager

    @Inject
    lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    private lateinit var activityResultLauncher: ActivityResultLauncher<String>
    private lateinit var notificationPermissionCallback: NotificationPermissionCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityResultLauncher()

        activateFirebaseRemoteConfig()
        loadFirebaseRemoteConfigDefaults()

        setContent {
            FakeCallsTheme {
                FakeCallsAppHost(applicationRouter)
            }
        }

        supportFragmentManager.subscribeToDialogsRequests(lifecycleScope)

        subscribeToNotificationPermissionRequests(lifecycleScope)
    }

    private fun activateFirebaseRemoteConfig() {
        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(TAG, "Config params updated: $updated")
                } else {
                    Log.d(TAG, "Failed to update remote config parameters")
                }
            }
    }

    private fun loadFirebaseRemoteConfigDefaults() {
        firebaseRemoteConfig.setDefaultsAsync(R.xml.rc_defaults)
    }

    private fun FragmentManager.subscribeToDialogsRequests(
        lifecycleCoroutineScope: LifecycleCoroutineScope
    ) {
        lifecycleCoroutineScope.launchWhenResumed {
            dialogsDisplayer.dialogs.collect { dialog ->
                dialog.show(this@subscribeToDialogsRequests, "Dialog")
            }
        }
    }

    private fun subscribeToNotificationPermissionRequests(
        lifecycleCoroutineScope: LifecycleCoroutineScope
    ) {
        lifecycleCoroutineScope.launchWhenResumed {
            notificationPermissionManager.notificationPermissionRequests.collect { request ->
                requestNotificationsPermission(request)
            }
        }
    }

    @SuppressLint("InlinedApi")
    private fun requestNotificationsPermission(request: NotificationPermissionCallback) {
        notificationPermissionCallback = request

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionCallback.doWhenGranted()
        } else {
            activityResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun initActivityResultLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) {
                    notificationPermissionCallback.doWhenGranted()
                } else {
                    notificationPermissionCallback.doWhenNotGranted()
                }
            }
    }

    companion object {
        private const val TAG = "Main activity"
    }
}
