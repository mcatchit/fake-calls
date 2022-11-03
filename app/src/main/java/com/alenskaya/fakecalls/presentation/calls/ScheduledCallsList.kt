package com.alenskaya.fakecalls.presentation.calls

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alenskaya.fakecalls.presentation.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.presentation.calls.ui.ScheduledCallRow

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
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        modifier = modifier
    )
}
