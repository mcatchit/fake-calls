package com.alenskaya.fakecalls.presentation

import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * Provides notification permission requests to MainActivity.
 * Bad solution, because it is not expandable and won't work for others permission requests.
 */
class NotificationPermissionManager(
    private val externalScope: CoroutineScope
) {

    val notificationPermissionRequests = MutableSharedFlow<NotificationPermissionCallback>()

    /**
     * Requests notification permission.
     * @param callback - accepts results.
     */
    fun requestNotificationPermission(callback: NotificationPermissionCallback) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            callback.doWhenGranted()
        } else {
            externalScope.launch {
                notificationPermissionRequests.emit(callback)
            }
        }
    }
}

/**
 * Collects results of notification permission requests
 */
class NotificationPermissionCallback(
    val doWhenGranted: () -> Unit,
    val doWhenNotGranted: () -> Unit,
)
