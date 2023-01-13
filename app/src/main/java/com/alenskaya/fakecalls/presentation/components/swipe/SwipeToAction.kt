package com.alenskaya.fakecalls.presentation.components.swipe

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

/**
 * Component which launches action when slider reaches target direction.
 * @param targetDirection - where slider should be swiped to.
 * @param slider - component of slider.
 * @param doOnDirectionReachedAction - called when slider is swiped successfully to target destination.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToAction(
    targetDirection: SwipeDirection,
    slider: @Composable () -> Unit,
    doOnDirectionReachedAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val swipeableState = rememberSwipeableState(targetDirection.opposite())

    var sliderSize by remember { mutableStateOf(0) }

    swipeableState.SubscribeToDirection(targetSwipeDirection = targetDirection) {
        doOnDirectionReachedAction()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .makeSwipeable(
                    state = swipeableState,
                    sliderSize = sliderSize
                )
        ) {
            Box(
                Modifier
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .onGloballyPositioned { layout ->
                        if (sliderSize == 0) {
                            sliderSize = layout.size.width
                        }
                    }
            ) {
                slider()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SwipeableState<SwipeDirection>.SubscribeToDirection(
    targetSwipeDirection: SwipeDirection,
    action: () -> Unit
) {
    if (this.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                if (currentValue == targetSwipeDirection) {
                    action()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun Modifier.makeSwipeable(
    state: SwipeableState<SwipeDirection>,
    sliderSize: Int
): Modifier =
    composed {

        var anchors by remember {
            mutableStateOf(mapOf(0f to SwipeDirection.LEFT, 0f to SwipeDirection.RIGHT))
        }

        onGloballyPositioned { layout ->
            val left = layout.positionInParent().x
            val right = left + layout.size.width - sliderSize
            anchors = mutableMapOf(left to SwipeDirection.LEFT, right to SwipeDirection.RIGHT)

        }.run {
            if (anchors.size > 1) {
                swipeable(
                    state = state,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal
                )
            } else {
                this
            }
        }
    }
