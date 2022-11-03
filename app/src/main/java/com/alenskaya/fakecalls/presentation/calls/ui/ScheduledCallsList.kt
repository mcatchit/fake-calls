package com.alenskaya.fakecalls.presentation.calls.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.alenskaya.fakecalls.presentation.calls.model.CallsScreenCallModel

/**
 * Titled expandable ist of scheduled calls.
 * @param calls - displayed calls.
 * @param editCallAction - action of editing a call.
 * @param deleteCallAction - action of deleting a call.
 * @param modifier - modifier.
 */
@Composable
fun ScheduledCallsList(
    calls: List<CallsScreenCallModel>,
    editCallAction: (CallsScreenCallModel) -> Unit,
    deleteCallAction: (CallsScreenCallModel) -> Unit,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    ExpandableCallsList(
        title = "Scheduled", //FIXME
        calls = calls,
        callToRowAction = { call ->
            {
                ScheduledCallRow(
                    call = call,
                    editCallAction = editCallAction,
                    deleteCallAction = deleteCallAction,
                    imageLoader = imageLoader,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        modifier = modifier
    )
}
