package com.udb.alarmapp.utils

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection


class CurvedBottomShape(private val curveRadius: Float): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(0f, size.height - curveRadius)
            arcTo(
                Rect(
                    left = 0f,
                    top = size.height - 2 * curveRadius,
                    right = 2 * curveRadius,
                    bottom = size.height
                ),
                180f,
                90f,
                false
            )
            lineTo(size.width - curveRadius, size.height)
            arcTo(
                Rect(
                    left = size.width - 2 * curveRadius,
                    top = size.height - 2 * curveRadius,
                    right = size.width,
                    bottom = size.height
                ),
                90f,
                90f,
                false
            )
            lineTo(size.width, 0f)
            close()
        }
        return Outline.Generic(path)
    }
}

