package com.alenskaya.fakecalls.presentation.main.calls.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

/**
 * Component containing call row actions
 */
@Composable
fun CallRowActionsButtons(
    actions: List<CallRowAction>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        actions.forEachIndexed { index, action ->
            CallRowActionButton(action = action)

            if (index != actions.size - 1) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Composable
private fun CallRowActionButton(
    action: CallRowAction
) {
    IconButton(
        onClick = action.onClickAction,
        modifier = Modifier.size(24.dp),
        interactionSource = MutableInteractionSource()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = action.iconId),
            contentDescription = action.description,
        )
    }
}
