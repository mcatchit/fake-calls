package com.alenskaya.fakecalls.presentation.calls.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.calls.model.CallsScreenCallModel

/**
 * Row which represents a scheduled call.
 * @param call - information about the call.
 * @param editCallAction - calls editing of the call.
 * @param deleteCallAction - calls deletion of the call.
 */
@Composable
fun ScheduledCallRow(
    call: CallsScreenCallModel,
    editCallAction: (CallsScreenCallModel) -> Unit,
    deleteCallAction: (CallsScreenCallModel) -> Unit,
    modifier: Modifier = Modifier
) {
    CallRow(
        call = call,
        actions = listOf(
            CallRowAction(
                iconId = R.drawable.edit_icon,
                description = "Edit call button", //FIXME
                onClickAction = {
                    editCallAction(call)
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
