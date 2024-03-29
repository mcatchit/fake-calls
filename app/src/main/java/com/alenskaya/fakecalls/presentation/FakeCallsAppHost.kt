package com.alenskaya.fakecalls.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alenskaya.fakecalls.presentation.main.FakeCallsNavHost
import com.alenskaya.fakecalls.presentation.main.MainBottomNavigationBar
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
import com.alenskaya.fakecalls.presentation.navigation.HomeNavigationDestination
import com.alenskaya.fakecalls.presentation.navigation.MainScreenNavigationDestination
import com.alenskaya.fakecalls.presentation.navigation.NavigationCommandExecutor
import com.alenskaya.fakecalls.presentation.navigation.NavigationDestination
import com.alenskaya.fakecalls.presentation.navigation.mainScreenDestinations

/**
 * Entry point of MainActivity.
 */
@Composable
fun FakeCallsAppHost(applicationRouter: ApplicationRouter) {
    val navController = rememberNavController().apply {
        BindRouter(applicationRouter = applicationRouter)
    }

    val currentBackStack by navController.currentBackStackEntryAsState()

    val currentDestination = currentBackStack?.destination

    val currentScreen = if (currentDestination != null) {
        mainScreenDestinations.find { it.destination == currentDestination.route }
    } else {
        HomeNavigationDestination
    }

    Scaffold(
        backgroundColor = getBackgroundColor(currentScreen),
        bottomBar = getBottomNavigationBar(applicationRouter, currentScreen)
    ) { innerPadding ->
        FakeCallsNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun NavHostController.BindRouter(
    applicationRouter: ApplicationRouter,
) {
    val navigationCommandExecutor = NavigationCommandExecutor(this)

    LaunchedEffect(applicationRouter.commands) {
        applicationRouter.commands.collect { command ->
            navigationCommandExecutor.execute(command)
        }
    }
}

@Composable
private fun getBackgroundColor(currentScreen: NavigationDestination?): Color {
    return if (currentScreen == HomeNavigationDestination) MaterialTheme.colors.primary
    else MaterialTheme.colors.background
}

private fun getBottomNavigationBar(
    applicationRouter: ApplicationRouter,
    currentScreen: MainScreenNavigationDestination?,
): @Composable () -> Unit {
    return {
        if (currentScreen is MainScreenNavigationDestination) {
            MainBottomNavigationBar(
                destinations = mainScreenDestinations,
                currentScreen = currentScreen,
                onDestinationChanged = { mainScreenDestination ->
                    applicationRouter.navigate(mainScreenDestination)
                }
            )
        }
    }
}
