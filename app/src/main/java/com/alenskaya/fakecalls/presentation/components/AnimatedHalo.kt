package com.alenskaya.fakecalls.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.theme.FakeCallsTheme
import com.alenskaya.fakecalls.presentation.theme.GreenHalo

/**
 * Animated circle halo displayed behind component.
 */
@Composable
fun AnimatedHalo(
    minSize: Dp,
    maxSize: Dp,
    color: Color,
    modifier: Modifier = Modifier,
    surroundedComponent: @Composable () -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition()

    val offsetAnimation by infiniteTransition.animateValue(
        initialValue = minSize,
        targetValue = maxSize,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing, delayMillis = 300),
            repeatMode = RepeatMode.Reverse
        )
    )

    Halo(offsetAnimation, maxSize, color, surroundedComponent, modifier)
}

@Composable
private fun Halo(
    size: Dp,
    maxSize: Dp,
    color: Color,
    surroundedComponent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.size(maxSize)) {
        Box(
            modifier = Modifier
                .size(size)
                .background(color = color, shape = CircleShape)
                .align(Alignment.Center)
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            surroundedComponent()
        }
    }
}

@Preview
@Composable
private fun AnimatedHaloPreview() {
    FakeCallsTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            AnimatedHalo(100.dp, 130.dp, GreenHalo, modifier = Modifier) {
                Image(
                    painter = painterResource(id = R.drawable.incoming_call_icon),
                    contentDescription = "Accept",
                    modifier = Modifier
                        .size(100.dp)
                )
            }
        }
    }
}
