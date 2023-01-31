package com.alenskaya.fakecalls.presentation

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
    lateinit var permissionManager: PermissionManager

    @Inject
    lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>

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
                dialog.show(this@subscribeToDialogsRequests, DIALOG_TAG)
            }
        }
    }

    private fun subscribeToNotificationPermissionRequests(
        lifecycleCoroutineScope: LifecycleCoroutineScope
    ) {
        lifecycleCoroutineScope.launchWhenResumed {
            permissionManager.notificationPermissionRequests.collect { request ->
                requestPermission(request)
            }
        }
    }

    private fun requestPermission(permission: String) {
        if (ContextCompat.checkSelfPermission(this, permission)
            == PackageManager.PERMISSION_GRANTED
        ) {
            permissionManager.permissionProcessed(permission, true)
        } else {
            activityResultLauncher.launch(arrayOf(permission))
        }
    }

    private fun initActivityResultLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
                results.keys.forEach { permission->
                    val isGranted = results[permission] ?: error("Illegal state")

                    permissionManager.permissionProcessed(permission, isGranted)
                }
            }
    }

    companion object {
        private const val TAG = "Main activity"
        private const val DIALOG_TAG = "Dialog"
    }
}
