package com.alenskaya.fakecalls.presentation.execution.screen.calling

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.alenskaya.fakecalls.presentation.theme.CallingGradientDark
import com.alenskaya.fakecalls.presentation.theme.FakeCallsTheme
import com.alenskaya.fakecalls.presentation.theme.CallingGradientLight
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Screen shown when call is executing but not accepted or declined yet.
 */
@Composable
fun CallingScreen(
    imageLoader: ImageLoader,
    executionStrings: ExecutionStrings,
    state: CallExecutionScreenState.Calling,
    doOnAccept: () -> Unit,
    doOnDecline: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.0f to CallingGradientDark,
                        0.5f to CallingGradientLight,
                        1f to CallingGradientDark
                    )
                )
            )
    ) {
        val (incomingCallTitle, contact, declined, buttons) = createRefs()

        IncomingCallTitle(
            isVisible = !state.isDeclined,
            text = executionStrings.incomingCall(),
            textColor = MaterialTheme.colors.onPrimary,
            modifier = Modifier.constrainAs(incomingCallTitle) {
                top.linkTo(parent.top, margin = 36.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Contact(
            imageLoader = imageLoader,
            callExecutionParams = state.callExecutionParams,
            textColor = MaterialTheme.colors.onPrimary,
            modifier = Modifier.constrainAs(contact) {
                top.linkTo(parent.top, margin = 136.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        MissedCallError(
            isVisible = state.isDeclined,
            text = executionStrings.missedCall(),
            modifier = Modifier.constrainAs(declined) {
                top.linkTo(contact.bottom, margin = 44.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        ControlButtons(
            isVisible = !state.isDeclined,
            doOnAccept = doOnAccept,
            doOnDecline = doOnDecline,
            acceptDescription = executionStrings.accept(),
            declineDescription = executionStrings.decline(),
            modifier = Modifier
                .constrainAs(buttons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 124.dp)
                }
                .padding(horizontal = 40.dp)
        )
    }
}

@Preview
@Composable
private fun CallingPreview() {
    FakeCallsTheme {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(
                color = CallingGradientDark,
                darkIcons = false
            )
        }

        CallingScreen(
            imageLoader = LocalImageLoader.current,
            executionStrings = object : ExecutionStrings {
                override fun incomingCall() = "Incoming call"

                override fun photoDescription() = "Photo"

                override fun accept() = "Accept"

                override fun decline() = "Decline"

                override fun missedCall() = "Missed call"

                override fun callCompleted() = "Call completed"

                override fun ongoingCall() = "Ongoing call"

                override fun callExecutionTimeIcon() = "Call execution time"
            },
            state = CallExecutionScreenState.Calling(
                CallExecutionParams(
                    1,
                    "Pitt Stewart",
                    "+342342334",
                    "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
                    123
                ), false
            ),
            doOnAccept = {},
            doOnDecline = {}
        )
    }
}
