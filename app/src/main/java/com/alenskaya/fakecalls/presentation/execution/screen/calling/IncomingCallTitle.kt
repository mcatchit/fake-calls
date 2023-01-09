package com.alenskaya.fakecalls.presentation.execution.screen.calling

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Calling screen header.
 */
@Composable
fun IncomingCallTitle(
    text: String,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
        modifier = modifier
    ) {
        SmallTitle(text = text)
    }
}
