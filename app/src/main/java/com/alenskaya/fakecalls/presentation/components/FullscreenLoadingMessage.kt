package com.alenskaya.fakecalls.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Displays fullscreen text with loading progress
 * @param text - message text
 */
@Composable
fun FullScreenLoadingMessage(
    text: String,
    modifier: Modifier = Modifier
) {
    PlaceInCenter {
        LoadingProgress(
            text = text,
            modifier = modifier
                .align(Alignment.Center)
        )
    }
}