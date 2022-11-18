package com.alenskaya.fakecalls.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alenskaya.fakecalls.presentation.features.calls.CallsScreen
import com.alenskaya.fakecalls.presentation.features.create.CreateCallScreen
import com.alenskaya.fakecalls.presentation.features.home.HomeScreen
import com.alenskaya.fakecalls.presentation.navigation.CallsNavigationDestination
import com.alenskaya.fakecalls.presentation.navigation.HomeNavigationDestination
import com.alenskaya.fakecalls.presentation.navigation.create.CreateRoutes

/**
 * Application navigation host
 */
@Composable
fun FakeCallsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeNavigationDestination.destination,
        modifier = modifier
    ) {
        
        composable(route = HomeNavigationDestination.destination) {
            HomeScreen()
        }
        composable(route = CallsNavigationDestination.destination) {
            CallsScreen()
        }

        for (createRoute in CreateRoutes.allRoutes){
            composable(
                route = createRoute.route,
                arguments = createRoute.args
            ) { backStackEntry ->
                CreateCallScreen(mode = createRoute.getMode(backStackEntry))
            }
        }
    }
}
