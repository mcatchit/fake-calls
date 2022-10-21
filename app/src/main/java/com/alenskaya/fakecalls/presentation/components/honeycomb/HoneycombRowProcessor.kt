package com.alenskaya.fakecalls.presentation.components.honeycomb

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable

/**
 * Helps to split elements of honeycomb grid into rows.
 * @property elements - elements of the grid.
 * @property maxElementsInRow - number of elements in the biggest row.
 */
class HoneycombRowProcessor(
    private val elements: List<@Composable BoxScope.() -> Unit>,
    private val maxElementsInRow: Int,
    startWithBigLine: Boolean
) {
    private var processedElementsAmount = 0
    private var isBigLine = startWithBigLine

    /**
     * Next row availability indicator.
     */
    val hasNext: Boolean
        get() = processedElementsAmount < elements.size

    /**
     * Size of the next row.
     * Should be called only if [hasNext] = true.
     */
    val nextRowSize: Int
        get() = if (isBigLine) maxElementsInRow else maxElementsInRow - 1

    /**
     * Elements of the next row.
     * Should be called only if [hasNext] = true.
     */
    val nextRowElements: List<@Composable (BoxScope.() -> Unit)>
        get() = getElements()

    private fun getElements(): List<@Composable (BoxScope.() -> Unit)> {
        val notProcessedElementsAmount = elements.size - processedElementsAmount

        val notEmptyElementsInCurrentRowAmount =
            if (notProcessedElementsAmount < nextRowSize) notProcessedElementsAmount else nextRowSize

        val currentElementsList = elements.subList(
            processedElementsAmount,
            processedElementsAmount + notEmptyElementsInCurrentRowAmount
        )

        processedElementsAmount += notEmptyElementsInCurrentRowAmount
        isBigLine = !isBigLine

        return currentElementsList
    }
}
