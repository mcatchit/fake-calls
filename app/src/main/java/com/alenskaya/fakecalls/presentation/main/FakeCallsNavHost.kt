package com.alenskaya.fakecalls.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alenskaya.fakecalls.presentation.main.calls.CallsScreen
import com.alenskaya.fakecalls.presentation.main.create.CreateCallScreen
import com.alenskaya.fakecalls.presentation.main.home.HomeScreen
import com.alenskaya.fakecalls.presentation.main.phonebook.PhonebookScreen
import com.alenskaya.fakecalls.presentation.navigation.CallsNavigationDestination
import com.alenskaya.fakecalls.presentation.navigation.HomeNavigationDestination
import com.alenskaya.fakecalls.presentation.navigation.PhonebookScreenNavigationDestination
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
        composable(route = PhonebookScreenNavigationDestination.destination) {
            PhonebookScreen()
        }
        composable(route = CallsNavigationDestination.destination) {
            CallsScreen()
        }

        for (createRoute in CreateRoutes.allRoutes) {
            composable(
                route = createRoute.route,
                arguments = createRoute.args
            ) { backStackEntry ->
                CreateCallScreen(mode = createRoute.getMode(backStackEntry))
            }
        }
    }
}
