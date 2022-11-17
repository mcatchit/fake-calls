package com.alenskaya.fakecalls.presentation.navigation

/**
 * Basic interface of any navigation command which has a destination.
 */
interface NavigationDestination: NavigationCommand {
    /**
     * Destination to navigate to
     */
    val destination: String
}
