package com.alenskaya.fakecalls.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alenskaya.fakecalls.presentation.execution.CallsScheduler
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
import com.alenskaya.fakecalls.presentation.navigation.NavigationDestination
import com.alenskaya.fakecalls.presentation.navigation.HomeNavigationDestination
import com.alenskaya.fakecalls.presentation.navigation.MainScreenNavigationDestination
import com.alenskaya.fakecalls.presentation.navigation.NavigationCommandExecutor
import com.alenskaya.fakecalls.presentation.navigation.mainScreenDestinations
import com.alenskaya.fakecalls.presentation.theme.FakeCallsTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Root app activity
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var callsScheduler: CallsScheduler

    @Inject
    lateinit var applicationRouter: ApplicationRouter

    @Inject
    lateinit var dialogsDisplayer: DialogsDisplayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FakeCallsTheme {
                FakeCallsApp(applicationRouter)
            }
        }

        supportFragmentManager
            .subscribeOnDialogsRequests(dialogsDisplayer, lifecycleScope)
    }
}

private fun FragmentManager.subscribeOnDialogsRequests(
    dialogsDisplayer: DialogsDisplayer,
    lifecycleCoroutineScope: LifecycleCoroutineScope
) {
    lifecycleCoroutineScope.launchWhenResumed {
        dialogsDisplayer.dialogs.collect { dialog ->
            dialog.show(this@subscribeOnDialogsRequests, "Dialog")
        }
    }
}

@Composable
private fun FakeCallsApp(applicationRouter: ApplicationRouter) {
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
