package com.alenskaya.fakecalls.presentation.execution.screen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alenskaya.fakecalls.presentation.execution.screen.calling.SmallTitle

/**
 * Calling screen header.
 */
@Composable
fun IncomingCallTitle(
    text: String,
    textColor: Color,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
        modifier = modifier
    ) {
        SmallTitle(text = text, color = textColor)
    }
}
