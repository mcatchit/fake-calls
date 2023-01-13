package com.alenskaya.fakecalls.presentation.execution.screen.ongoing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alenskaya.fakecalls.presentation.execution.screen.calling.SmallTitle
import com.alenskaya.fakecalls.presentation.theme.CallingGreen

/**
 * Message that call is completed.
 * @param isVisible - is it visible (required for animation).
 * @param text - message text.
 * @param modifier - modifier.
 */
@Composable
fun CallCompleted(
    isVisible: Boolean,
    text: String,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        modifier = modifier
    ) {
        SmallTitle(text = text, color = CallingGreen)
    }
}
