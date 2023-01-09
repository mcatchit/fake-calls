package com.alenskaya.fakecalls.presentation.execution.screen

import androidx.compose.runtime.Composable
import com.alenskaya.fakecalls.presentation.execution.model.CallExecutionParams
import com.alenskaya.fakecalls.presentation.execution.screen.calling.CallingScreen

/**
 * Ui of call execution screen.
 * @param callExecutionParams - parameters of an incoming call.
 * @param exitApplicationAction - action to exit application, called when user chooses to decline a call.
 */
@Composable
fun CallExecutionScreen(
    callExecutionParams: CallExecutionParams,
    exitApplicationAction: () -> Unit,
) {
    CallingScreen(
        callExecutionParams = callExecutionParams,
        cancelCallAction = exitApplicationAction
    )
}
