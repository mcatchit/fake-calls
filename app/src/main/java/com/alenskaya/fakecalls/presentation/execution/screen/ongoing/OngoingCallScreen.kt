package com.alenskaya.fakecalls.presentation.execution.screen.ongoing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.ImageLoader
import coil.compose.LocalImageLoader
import com.alenskaya.fakecalls.presentation.execution.ExecutionStrings
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.execution.screen.CallExecutionScreenState
import com.alenskaya.fakecalls.presentation.execution.screen.ui.Contact
import com.alenskaya.fakecalls.presentation.execution.screen.ui.IncomingCallTitle
import com.alenskaya.fakecalls.presentation.theme.FakeCallsTheme
import com.alenskaya.fakecalls.presentation.theme.OngoingCallBackground
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Screen for accepted call.
 * @param imageLoader - application image loader.
 * @param executionStrings - strings for call execution.
 * @param state - state of ongoing call.
 * @param endCallAction - action to end call.
 */
@Composable
fun OngoingCallScreen(
    imageLoader: ImageLoader,
    executionStrings: ExecutionStrings,
    state: CallExecutionScreenState.OngoingCall,
    endCallAction: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = OngoingCallBackground)
    ) {
        val (incomingCallTitle, contact, time, completed, buttons) = createRefs()

        IncomingCallTitle(
            isVisible = !state.isCompleted,
            text = executionStrings.ongoingCall(),
            textColor = MaterialTheme.colors.onSecondary,
            modifier = Modifier.constrainAs(incomingCallTitle) {
                top.linkTo(parent.top, margin = 36.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Contact(
            imageLoader = imageLoader,
            callExecutionParams = state.callExecutionParams,
            textColor = MaterialTheme.colors.onSecondary,
            modifier = Modifier.constrainAs(contact) {
                top.linkTo(parent.top, margin = 136.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        CallExecutionTime(
            time = state.time,
            description = executionStrings.callExecutionTimeIcon(),
            modifier = Modifier.constrainAs(time) {
                top.linkTo(contact.bottom, margin = 44.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        CallCompleted(
            isVisible = state.isCompleted,
            text = executionStrings.callCompleted(),
            modifier = Modifier.constrainAs(completed) {
                top.linkTo(time.bottom, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        OngoingControlButtons(
            isVisible = !state.isCompleted,
            doOnEnd = endCallAction,
            modifier = Modifier
                .constrainAs(buttons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 100.dp)
                }
        )
    }
}

@Preview
@Composable
private fun OngoingCallPreview() {
    FakeCallsTheme {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(
                color = OngoingCallBackground,
                darkIcons = false
            )
        }

        OngoingCallScreen(
            imageLoader = LocalImageLoader.current,
            executionStrings = object : ExecutionStrings {
                override fun incomingCall() = "Incoming call"

                override fun ongoingCall() = "Ongoing call"

                override fun callExecutionTimeIcon() = "Call execution time"

                override fun photoDescription() = "Photo"

                override fun accept() = "Accept"

                override fun decline() = "Decline"

                override fun missedCall() = "Missed call"

                override fun callCompleted() = "Call completed"
            },
            state = CallExecutionScreenState.OngoingCall(
                callExecutionParams = CallExecutionParams(
                    1,
                    "Kendall Stewart",
                    "+342342334",
                    "https://tinypng.com/images/social/website.jpg",
                    123
                ),
                time = "00:25",
                isCompleted = false,
            ),
            endCallAction = { println("Cancel") }
        )
    }
}
