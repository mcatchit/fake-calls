package com.alenskaya.fakecalls.presentation.main.calls.ui.row

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenCallModel

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
    editDescription: String,
    deleteCallAction: (CallsScreenCallModel) -> Unit,
    deleteDescription: String,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    CallRow(
        call = call,
        actions = listOf(
            CallRowAction(
                iconId = R.drawable.edit_icon,
                description = editDescription,
                onClickAction = {
                    editCallAction(call)
                }
            ),
            CallRowAction(
                iconId = R.drawable.delete_icon,
                description = deleteDescription,
                onClickAction = {
                    deleteCallAction(call)
                }
            )
        ),
        imageLoader = imageLoader,
        modifier = modifier
    )
}
