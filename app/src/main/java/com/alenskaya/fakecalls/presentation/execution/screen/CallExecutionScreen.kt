package com.alenskaya.fakecalls.presentation.execution.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.execution.screen.CallExecutionScreenViewModel
import com.alenskaya.fakecalls.presentation.execution.screen.CallingScreen

/**
 * Ui of call execution screen.
 * @param callExecutionParams - parameters of an incoming call.
 * @param exitApplicationAction - action to exit application, called when user chooses to decline a call.
 * @param viewModel - view model of the screen.
 */
@Composable
fun CallExecutionScreen(
    callExecutionParams: CallExecutionParams,
    exitApplicationAction: () -> Unit,
    viewModel: CallExecutionScreenViewModel = hiltViewModel()
) {
    CallingScreen(
        imageLoader = viewModel.imageLoader,
        executionStrings = viewModel.executionStrings,
        callExecutionParams = callExecutionParams,
        doOnAccept = {},
        doOnDecline = exitApplicationAction
    )
}
