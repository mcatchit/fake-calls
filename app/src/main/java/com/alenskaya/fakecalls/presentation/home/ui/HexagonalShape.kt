package com.alenskaya.fakecalls.presentation.home.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

/**
 * Custom hexagonal shape. Mostly used for displaying items in honeycomb.
 */
class HexagonalShape(
    private val cornerRadius: Int = 0
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                polygon(
                    size.height / 2,
                    size.center
                )
            }
        )
    }

    private fun Path.polygon(radius: Float, center: Offset) {
        val sides = 6

        val angle = 2.0 * Math.PI / sides
        moveTo(
            x = center.x + (radius * sin(0.0)).toFloat(),
            y = center.y + (radius * cos(0.0)).toFloat()
        )
        for (i in 1 until sides) {
            lineTo(
                x = center.x + (radius * sin(angle * i)).toFloat(),
                y = center.y + (radius * cos(angle * i)).toFloat()
            )
        }
        close()
    }
}
