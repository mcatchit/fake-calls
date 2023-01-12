package com.alenskaya.fakecalls.presentation.execution.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.execution.screen.calling.CallingScreen
import com.alenskaya.fakecalls.presentation.execution.screen.ongoing.OngoingCallScreen

/**
 * Ui of call execution screen.
 * @param callExecutionParams - parameters of an incoming call.
 * @param endCallAction - action to end call.
 */
@Composable
fun CallExecutionScreen(
    callExecutionParams: CallExecutionParams,
    endCallAction: () -> Unit,
    viewModel: CallExecutionScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.init(endCallAction, callExecutionParams)
    }

    val state by viewModel.screenState.collectAsState()

    AnimateTransition(state = state) {
        when (state) {
            is CallExecutionScreenState.Calling -> {
                CallingScreen(
                    imageLoader = viewModel.imageLoader,
                    executionStrings = viewModel.executionStrings,
                    state = state as CallExecutionScreenState.Calling,
                    doOnAccept = { viewModel.sendEvent(CallingScreenEvent.AcceptCall) },
                    doOnDecline = { viewModel.sendEvent(CallingScreenEvent.DeclineCall) }
                )
            }
            is CallExecutionScreenState.OngoingCall -> {
                OngoingCallScreen(
                    imageLoader = viewModel.imageLoader,
                    executionStrings = viewModel.executionStrings,
                    state = state as CallExecutionScreenState.OngoingCall,
                    endCallAction = { viewModel.sendEvent(OngoingCallScreenEvent.EndCall) }
                )
            }

            CallExecutionScreenState.Default -> Unit
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimateTransition(state: CallExecutionScreenState, content: @Composable () -> Unit) {
    AnimatedContent(
        targetState = state,
        transitionSpec = {
            if (initialState != CallExecutionScreenState.Default
                && targetState::class.java != initialState::class.java
            ) {
                scaleIn() with ExitTransition.None
            } else {
                EnterTransition.None with ExitTransition.None
            }
        }
    ) {
        content.invoke()
    }
}
