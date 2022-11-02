package com.alenskaya.fakecalls.presentation.calls.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.calls.model.CallsScreenCallModel

/**
 * Row which represents a completed call.
 * @param call - information about the call.
 * @param repeatCallAction - calls repeating of the call.
 * @param deleteCallAction - calls deletion of the call.
 */
@Composable
fun CompletedCallRow(
    call: CallsScreenCallModel,
    repeatCallAction: (CallsScreenCallModel) -> Unit,
    deleteCallAction: (CallsScreenCallModel) -> Unit,
    modifier: Modifier = Modifier
) {
    CallRow(
        call = call,
        actions = listOf(
            CallRowAction(
                iconId = R.drawable.repeat_icon,
                description = "Repeat call button", //FIXME
                onClickAction = {
                    repeatCallAction(call)
                }
            ),
            CallRowAction(
                iconId = R.drawable.delete_icon,
                description = "Delete call button", //FIXME
                onClickAction = {
                    deleteCallAction(call)
                }
            )
        ),
        modifier = modifier
    )
}
