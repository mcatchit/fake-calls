package com.alenskaya.fakecalls.presentation.navigation

import androidx.navigation.NamedNavArgument

/**
 * Used to declare routes, where final destination is unknown (f.e. destinations with arguments).
 * Implementation should provide a method to create a final destination.
 *
 * [route] and [args] are passed to "composable" extension of NavGraphBuilder.
 * Example:
 * composable(
 *      route = createRoute.route,
 *      arguments = createRoute.args
 *      )
 *
 * Implementation should provide a method to create a final destination.
 * Example:
 *      fun createDestination(callId: Int): NavigationDestination
 */
interface Route {

    /**
     * Route path
     */
    val route: String

    /**
     * List of provided arguments
     */
    val args: List<NamedNavArgument>
}
