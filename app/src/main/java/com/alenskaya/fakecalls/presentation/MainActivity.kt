package com.alenskaya.fakecalls.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alenskaya.fakecalls.presentation.theme.FakeCallsTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Root app activity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FakeCallsTheme {
                FakeCallsApp()
            }
        }
    }
}

@Composable
fun FakeCallsApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()

    val currentDestination = currentBackStack?.destination

    val allScreens = listOf(Home, Calls)

    val currentScreen = allScreens.find { it.route == currentDestination?.route } ?: Home

    val backgroundColor =
        if (currentScreen == Home) MaterialTheme.colors.primary else MaterialTheme.colors.background

    Scaffold(
        backgroundColor = backgroundColor,

        bottomBar = {
            BottomNavigationBar(
                destinations = allScreens,
                currentScreen = currentScreen,
                onDestinationChanged = {
                    navController.navigateSingleTopTo(it.route)
                }
            )
        }
    ) { innerPadding ->
        FakeCallsNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
