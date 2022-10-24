package com.alenskaya.fakecalls.presentation.home.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Home screen title
 */
@Composable
fun CreateNewCallTitle(modifier: Modifier = Modifier) {
    Text(
        text = "Create new call",
        modifier = modifier,
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.onSecondary
    )
}
