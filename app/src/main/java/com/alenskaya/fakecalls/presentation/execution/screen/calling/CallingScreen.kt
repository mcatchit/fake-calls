package com.alenskaya.fakecalls.presentation.execution.screen.calling

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.LocalImageLoader
import com.alenskaya.fakecalls.presentation.execution.ExecutionStrings
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.execution.screen.CallExecutionScreenViewModel
import com.alenskaya.fakecalls.presentation.execution.screen.CallExecutionScreenEvent
import com.alenskaya.fakecalls.presentation.execution.screen.CallExecutionScreenState
import com.alenskaya.fakecalls.presentation.theme.GradientDark
import com.alenskaya.fakecalls.presentation.theme.FakeCallsTheme
import com.alenskaya.fakecalls.presentation.theme.GradientLight
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Screen shown when call is executing but not accepted or declined yet.
 */
@Composable
fun CallingScreen(
    callExecutionParams: CallExecutionParams,
    cancelCallAction: () -> Unit,
    viewModel: CallExecutionScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.init(cancelCallAction, callExecutionParams)
    }

    val state by viewModel.screenState.collectAsState()

    CallingScreen(
        imageLoader = viewModel.imageLoader,
        executionStrings = viewModel.executionStrings,
        state = state,
        doOnAccept = { viewModel.sendEvent(CallExecutionScreenEvent.AcceptCall) },
        doOnDecline = { viewModel.sendEvent(CallExecutionScreenEvent.DeclineCall) }
    )
}

@Composable
private fun CallingScreen(
    imageLoader: ImageLoader,
    executionStrings: ExecutionStrings,
    state: CallExecutionScreenState,
    doOnAccept: () -> Unit,
    doOnDecline: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.0f to GradientDark,
                        0.5f to GradientLight,
                        1f to GradientDark
                    )
                )
            )
    ) {
        val (incomingCallTitle, contact, declined, buttons) = createRefs()

        IncomingCallTitle(
            isVisible = !state.isDeclined,
            text = executionStrings.incomingCall(),
            modifier = Modifier.constrainAs(incomingCallTitle) {
                top.linkTo(parent.top, margin = 36.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Contact(
            imageLoader = imageLoader,
            callExecutionParams = state.callExecutionParams,
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
                color = GradientDark,
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
            },
            state = CallExecutionScreenState(
                CallExecutionParams(
                    1,
                    "Kendall Stewart",
                    "+342342334",
                    "https://tinypng.com/images/social/website.jpg",
                    123
                ), false
            ),
            doOnAccept = {},
            doOnDecline = {}
        )
    }
}
