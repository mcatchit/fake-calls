package com.alenskaya.fakecalls.presentation.execution.screen.calling

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.components.AnimatedHalo
import com.alenskaya.fakecalls.presentation.components.swipe.DoubleSidedSwipeToAction
import com.alenskaya.fakecalls.presentation.theme.GreenHalo

/**
 * Buttons for call accepting/declining.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ControlButtons(
    isVisible: Boolean,
    doOnAccept: () -> Unit,
    doOnDecline: () -> Unit,
    acceptDescription: String,
    declineDescription: String,
    modifier: Modifier
) {
    val declineButton = @Composable {
        Box(modifier = Modifier.padding(10.dp)) {
            ControlButton(
                image = R.drawable.decline_call_icon,
                description = declineDescription
            )
        }
    }
    val acceptButton = @Composable {
        AnimatedHalo(minSize = 60.dp, maxSize = 80.dp, color = GreenHalo) {
            ControlButton(
                image = R.drawable.incoming_call_icon,
                description = acceptDescription
            )
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        exit = scaleOut() + fadeOut(),
        modifier = modifier
    ) {
        DoubleSidedSwipeToAction(
            doOnAccept = doOnAccept,
            doOnCancel = doOnDecline,
            acceptSlider = acceptButton,
            cancelSlider = declineButton,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun ControlButton(
    image: Int,
    description: String
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = description,
        modifier = Modifier
            .size(60.dp)
    )
}
