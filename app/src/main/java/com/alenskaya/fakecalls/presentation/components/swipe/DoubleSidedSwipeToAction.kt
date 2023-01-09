package com.alenskaya.fakecalls.presentation.components.swipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenskaya.fakecalls.presentation.theme.FakeCallsTheme

/**
 * Double sided swipe to action for binary actions (which can be either accepted or cancelled).
 * Right slider should be swiped to the screen center in case of action accepting.
 * Left slider should be swiped to the screen center in case of action cancelling.
 */
@Composable
fun DoubleSidedSwipeToAction(
    doOnAccept: () -> Unit,
    doOnCancel: () -> Unit,
    acceptSlider: @Composable () -> Unit,
    cancelSlider: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SwipeToAction(
            targetDirection = SwipeDirection.RIGHT,
            slider = cancelSlider,
            doOnDirectionReachedAction = doOnCancel,
            modifier = Modifier
                .weight(1f)
        )
        SwipeToAction(
            targetDirection = SwipeDirection.LEFT,
            slider = acceptSlider,
            doOnDirectionReachedAction = doOnAccept,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Preview
@Composable
private fun DoubleSidedSwipeToActionPreview() {
    FakeCallsTheme {
        DoubleSidedSwipeToAction(
            doOnAccept = { println("Accepted") },
            doOnCancel = { println("Decline") },
            acceptSlider = {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Green)
                )
            },
            cancelSlider = {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Red)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
