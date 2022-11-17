package com.alenskaya.fakecalls.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.alenskaya.fakecalls.presentation.navigation.MainScreenNavigationDestination

/**
 * Bottom navigation bar of main screen
 */
@Composable
fun MainBottomNavigationBar(
    destinations: List<MainScreenNavigationDestination>,
    currentScreen: MainScreenNavigationDestination,
    onDestinationChanged: (MainScreenNavigationDestination) -> Unit
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        for (destination in destinations) {
            MenuIcon(
                icon = destination.iconResourceId,
                description = destination.destination,
                isSelected = destination == currentScreen,
                onSelected = { onDestinationChanged(destination) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
private fun MenuIcon(
    icon: Int,
    description: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                onClick = onSelected,
                indication = null
            )
    ) {
        Icon(
            contentDescription = description,
            imageVector = ImageVector.vectorResource(id = icon),
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .height(6.dp)
                .fillMaxWidth()
                .background(if (isSelected) MaterialTheme.colors.primaryVariant else Color.Transparent)
        )
    }
}

