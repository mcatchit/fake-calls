package com.alenskaya.fakecalls.presentation.execution

import android.content.BroadcastReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Allows execute asynchronous operations in broadcast receivers.
 * @param scope - coroutine scope.
 * @param action - suspend action to be executed.
 */
fun BroadcastReceiver.doAsync(scope: CoroutineScope, action: suspend () -> Unit) {
    val pendingResult: BroadcastReceiver.PendingResult = goAsync()

    scope.launch(Dispatchers.Default) {
        try {
            action()
        } finally {
            // Must call finish() so the BroadcastReceiver can be recycled
            pendingResult.finish()
        }
    }
}
