package com.alenskaya.fakecalls.presentation.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp

/**
 * Displays passed [elements] as honeycomb
 * @param width - width of the grid
 * @param padding - padding between cells
 * @param elements - displayable elements
 * @param modifier - compose modifier
 * @param maxElementsInRow - amount of elements in the biggest row
 */
@Composable
fun HoneyCombGrid(
    width: Float,
    padding: Float,
    elements: List<@Composable BoxScope.() -> Unit>,
    modifier: Modifier = Modifier,
    maxElementsInRow: Int = 3
) {
    val availableCellWidth =
        (width - (padding * (maxElementsInRow + 1))) / maxElementsInRow.toFloat()
    val cellSize = hexagonalSize(availableCellWidth)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = padding.dp)
    ) {
        val rowProcessor = HoneycombRowProcessor(elements, maxElementsInRow)

        while (rowProcessor.hasNext) {
            HoneyCombRow(
                size = rowProcessor.nextRowSize,
                elements = rowProcessor.nextRowElements,
                cellSize = cellSize,
                padding = padding
            )
        }
    }
}

@Composable
private fun HoneyCombRow(
    size: Int,
    elements: List<@Composable BoxScope.() -> Unit>,
    cellSize: Size,
    padding: Float
) {
    val gap = size - elements.size
    val displayableElements = if (gap == 0) elements else elements.fulfill(gap)

    Row(
        horizontalArrangement = Arrangement.spacedBy(padding.dp)
    ) {
        displayableElements.forEach { element ->
            HoneyCombCell(height = cellSize.height, width = cellSize.width) {
                element()
            }
        }
    }
}

@Composable
private fun EmptyCell() {
    Box(Modifier)
}

private fun List<@Composable BoxScope.() -> Unit>.fulfill(gap: Int) =
    mutableListOf<@Composable (BoxScope.() -> Unit)>().apply {
        addAll(this)
        repeat(gap) {
            add {
                EmptyCell()
            }
        }
    }
