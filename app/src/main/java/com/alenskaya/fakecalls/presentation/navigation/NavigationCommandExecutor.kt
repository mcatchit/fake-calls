package com.alenskaya.fakecalls.presentation.navigation

import androidx.navigation.NavHostController

/**
 * Calls [navHostController] methods according to passed command.
 */
class NavigationCommandExecutor(
    private val navHostController: NavHostController
) {

    /**
     * Executes [command]
     */
    fun execute(command: NavigationCommand) {
        with(navHostController) {
            when (command) {
                is NavigateBack -> navigateUp()
                is MainScreenNavigationDestination -> navigateSingleTopTo(command.destination)
                is NavigationDestination -> navigate(command.destination)
            }
        }
    }
}
