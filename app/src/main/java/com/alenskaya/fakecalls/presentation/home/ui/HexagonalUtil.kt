package com.alenskaya.fakecalls.presentation.home.ui

import androidx.compose.ui.geometry.Size
import kotlin.math.pow

/**
 * Counts size of hexagonal item according to required [width]
 * @return size of hexagonal
 */
fun hexagonalSize(width: Float): Size {
    return Size(
        width = width,
        height = width * 2 / 3f.pow(0.5f)
    )
}
