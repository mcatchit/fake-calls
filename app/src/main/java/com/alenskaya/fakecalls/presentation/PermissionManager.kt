package com.alenskaya.fakecalls.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * Provides notification permission requests to MainActivity.
 * Bad solution, because it is not expandable and won't work for others permission requests.
 */
class PermissionManager(
    private val externalScope: CoroutineScope
) {

    private val permissionsMapping = mutableMapOf<String, PermissionRequestCallback>()

    val notificationPermissionRequests = MutableSharedFlow<String>()

    /**
     * Requests notification permission.
     * @param callback - accepts results.
     */
    fun requestPermission(permission: String, callback: PermissionRequestCallback) {
        permissionsMapping[permission] = callback

        externalScope.launch {
            notificationPermissionRequests.emit(permission)
        }
    }

    fun permissionProcessed(permission: String, result: Boolean) {
        callCallback(permission, result)
        removeCallback(permission)
    }

    private fun callCallback(permission: String, result: Boolean) {
        permissionsMapping[permission]?.invoke(result)
    }

    private fun removeCallback(permission: String) {
        permissionsMapping.remove(permission)
    }
}

typealias PermissionRequestCallback = (Boolean) -> Unit

