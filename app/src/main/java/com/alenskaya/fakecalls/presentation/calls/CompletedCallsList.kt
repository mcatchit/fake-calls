package com.alenskaya.fakecalls.presentation.calls

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alenskaya.fakecalls.presentation.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.presentation.calls.ui.CompletedCallRow

/**
 * Titled expandable ist of completed calls.
 * @param calls - displayed calls.
 * @param repeatCallAction - action of repeating a call.
 * @param deleteCallAction - action of deleting a call.
 * @param modifier - modifier.
 */
@Composable
fun CompletedCallsList(
    calls: List<CallsScreenCallModel>,
    repeatCallAction: (CallsScreenCallModel) -> Unit,
    deleteCallAction: (CallsScreenCallModel) -> Unit,
    modifier: Modifier = Modifier
) {
    ExpandableCallsList(
        title = "Completed", //FIXME
        calls = calls,
        callToRowAction = { call ->
            {
                CompletedCallRow(
                    call = call,
                    repeatCallAction = repeatCallAction,
                    deleteCallAction = deleteCallAction,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        modifier = modifier
    )
}
