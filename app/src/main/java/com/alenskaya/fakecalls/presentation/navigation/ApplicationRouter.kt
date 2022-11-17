package com.alenskaya.fakecalls.presentation.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * All application navigation should be executed using this router
 * @property externalScope - coroutine scope for emitting navigation actions
 */
class ApplicationRouter(
    private val externalScope: CoroutineScope
) {

    var commands = MutableSharedFlow<NavigationCommand>()

    /**
     * Emits [navigationCommand]
     */
    fun navigate(
        navigationCommand: NavigationCommand
    ) {
        externalScope.launch {
            commands.emit(navigationCommand)
        }
    }
}
