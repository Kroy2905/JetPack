package com.kroy.sseditor.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Shape

class BubbleShape(private val tailSize: Dp = 12.dp, private val cornerRadius: Dp = 16.dp, private val isSender: Boolean) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): androidx.compose.ui.graphics.Outline {
        val path = Path().apply {
            val tailSizePx = with(density) { tailSize.toPx() }
            val cornerRadiusPx = with(density) { cornerRadius.toPx() }

            if (isSender) {
                // Sender's tail
                moveTo(tailSizePx, cornerRadiusPx) // Move to the starting point
                lineTo(size.width - cornerRadiusPx, 0f) // Top edge
                lineTo(size.width, cornerRadiusPx) // Top right corner
                lineTo(size.width, size.height - cornerRadiusPx) // Right edge
                lineTo(size.width - cornerRadiusPx, size.height) // Bottom right corner
                lineTo(size.width - tailSizePx, size.height) // Bottom edge
                lineTo(size.width - tailSizePx, size.height - tailSizePx) // Tail pointer bottom
                lineTo(size.width, size.height / 2) // Point of the tail
                lineTo(size.width - tailSizePx, tailSizePx) // Right side of tail pointer
                lineTo(tailSizePx, tailSizePx) // Left side of tail pointer
                lineTo(tailSizePx, cornerRadiusPx) // Finish the shape
            } else {
                // Receiver's tail
                moveTo(cornerRadiusPx, 0f) // Top left corner
                lineTo(size.width - tailSizePx, 0f) // Top edge
                lineTo(size.width, cornerRadiusPx) // Top right corner
                lineTo(size.width, size.height - cornerRadiusPx) // Right edge
                lineTo(size.width - cornerRadiusPx, size.height) // Bottom right corner
                lineTo(tailSizePx, size.height) // Bottom edge
                lineTo(tailSizePx, size.height - tailSizePx) // Tail pointer bottom
                lineTo(0f, size.height / 2) // Point of the tail
                lineTo(tailSizePx, tailSizePx) // Left side of tail pointer
                lineTo(cornerRadiusPx, tailSizePx) // Left side of the top left corner
            }
            close()
        }
        return androidx.compose.ui.graphics.Outline.Generic(path)
    }
}
