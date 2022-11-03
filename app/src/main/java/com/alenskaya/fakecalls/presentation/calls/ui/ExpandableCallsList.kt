package com.alenskaya.fakecalls.presentation.calls.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alenskaya.fakecalls.presentation.calls.model.CallsScreenCallModel
import com.alenskaya.fakecalls.presentation.components.ExpandableSection

/**
 * Base expandable calls list.
 * @param title - list title.
 * @param calls - displayed calls.
 * @param callToRowAction - action of transforming a call to list row.
 * @param modifier - modifier.
 */
@Composable
fun ExpandableCallsList(
    title: String,
    calls: List<CallsScreenCallModel>,
    callToRowAction: (CallsScreenCallModel) -> (@Composable () -> Unit),
    modifier: Modifier = Modifier
) {
    ExpandableSection(
        title = title,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            calls.forEach { call ->
                callToRowAction(call).invoke()
            }
        }
    }
}
