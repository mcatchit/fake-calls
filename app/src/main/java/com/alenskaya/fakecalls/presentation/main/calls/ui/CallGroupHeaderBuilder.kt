package com.alenskaya.fakecalls.presentation.main.calls.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alenskaya.fakecalls.presentation.components.ExpandableTitle
import com.alenskaya.fakecalls.presentation.main.calls.model.CallsScreenListItem

/**
 * Builds header according to passed data.
 * @property doWhenClicked - action called when arrow button clicked.
 */
class CallGroupHeaderBuilder(
    private val doWhenClicked: () -> Unit
) {

    /**
     * Returns composable function which builds a header for [header].
     */
    fun buildHeader(header: CallsScreenListItem.Header): @Composable () -> Unit {
        return {
            ExpandableTitle(
                title = header.title,
                isOpened = header.isOpened,
                arrowClicked = doWhenClicked,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
