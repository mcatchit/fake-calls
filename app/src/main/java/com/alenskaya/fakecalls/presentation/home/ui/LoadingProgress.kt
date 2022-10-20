package com.alenskaya.fakecalls.presentation.home.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Loading suggestions progress on home screen
 */
@Composable
fun LoadingProgress(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = "Loading suggestions",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier.align(Alignment.Bottom)
        )
        LoadingDots(
            dotSize = 6.dp,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 3.dp)
        )
    }
}