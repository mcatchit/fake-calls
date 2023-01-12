package com.alenskaya.fakecalls.presentation.execution.screen.ongoing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.execution.screen.ui.ControlButton
import com.alenskaya.fakecalls.presentation.theme.DeclineCallTint

/**
 * Control buttons of ongoing call.
 * Contain fake buttons and end call button.
 * @param isVisible - are buttons visible (required for animation).
 * @param doOnEnd - called when end call button clicked.
 * @param modifier - modifier.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OngoingControlButtons(
    isVisible: Boolean,
    doOnEnd: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        exit = scaleOut() + fadeOut(),
        modifier = modifier
    ) {
        OngoingControlButtons(doOnCancel = doOnEnd)
    }
}

@Composable
private fun OngoingControlButtons(
    doOnCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        FakeButtons()
        Spacer(modifier = Modifier.height(60.dp))
        CancelButton(doOnClick = doOnCancel)
    }
}

@Composable
private fun FakeButtons(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        ControlButton(image = R.drawable.fake_control_button_microphone, description = "Microphone")
        Spacer(modifier = Modifier.width(60.dp))
        ControlButton(image = R.drawable.fake_control_button_message, description = "Message")
        Spacer(modifier = Modifier.width(60.dp))
        ControlButton(image = R.drawable.fake_control_button_record, description = "Record")
    }
}

@Composable
private fun CancelButton(doOnClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box {
        ControlButton(
            image = R.drawable.decline_call_icon,
            description = "Decline",
            tintColor = if (isPressed) DeclineCallTint else Color.Transparent,
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                doOnClick()
            }
        )
    }
}
