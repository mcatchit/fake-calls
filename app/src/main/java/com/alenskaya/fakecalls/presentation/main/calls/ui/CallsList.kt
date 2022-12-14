package com.alenskaya.fakecalls.presentation.main.calls.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenListItem

/**
 * Component that displays list of calls.
 * @param items - items of list.
 * @param callsListFactory - builds elements of calls list.
 * @param modifier - list modifier.
 */
@Composable
fun CallsList(
    items: List<CallsScreenListItem>,
    callsListFactory: CallsListFactory,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 24.dp)
    ) {
        items(items) { item ->
            when (item) {
                is CallsScreenListItem.Header -> callsListFactory.createHeaderRow(item).invoke()
                is CallsScreenListItem.CallItem -> callsListFactory.createCallRow(item).invoke()
            }
        }
    }
}
