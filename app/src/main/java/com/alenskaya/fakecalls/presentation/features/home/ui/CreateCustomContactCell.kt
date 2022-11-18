package com.alenskaya.fakecalls.presentation.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Create custom contact item on home screen
 */
@Composable
fun CreateCustomContactCell(
    onCreateClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primaryVariant)
            .clickable {
                onCreateClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "+",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSecondary,
        )
        Text(
            text = "custom",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSecondary,
        )
    }
}