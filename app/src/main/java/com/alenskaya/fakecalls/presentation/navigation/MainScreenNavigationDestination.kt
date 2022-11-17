package com.alenskaya.fakecalls.presentation.navigation

import com.alenskaya.fakecalls.R

/**
 * Main screen destinations (shown in bottom navigation bar)
 */
sealed interface MainScreenNavigationDestination : NavigationDestination {
    /**
     * Resource id of an icon
     */
    val iconResourceId: Int
}

object HomeNavigationDestination : MainScreenNavigationDestination {
    override val destination = "home"
    override val iconResourceId = R.drawable.home_icon
}

object CallsNavigationDestination : MainScreenNavigationDestination {
    override val destination = "calls"
    override val iconResourceId = R.drawable.calls_icon
}

/**
 * All destinations displayed in bottom navigation bar
 */
val mainScreenDestinations = listOf(HomeNavigationDestination, CallsNavigationDestination)
