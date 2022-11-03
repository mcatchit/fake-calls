package com.alenskaya.fakecalls.presentation.components.honeycomb

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Creates honeycomb cell with passed [width] and [height]
 * and fills it with [content].
 */
@Composable
fun HoneyCombCell(
    height: Float,
    width: Float,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .clip(HexagonalShape(20))
            .size(height = height.dp, width = width.dp)
    ) {
        content()
    }
}