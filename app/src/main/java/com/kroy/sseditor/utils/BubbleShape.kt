package com.kroy.sseditor.utils

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BubbleShape(tailSize: Dp): GenericShape {
    val density = LocalDensity.current

    return GenericShape { size, _ ->
        with(density) {
            val tailHeight = tailSize.toPx() // Converts Dp to Px using density
            val cornerRadius = 9.dp.toPx() // Adjust this for rounder corners
            val bubbleWidth = size.width
            val bubbleHeight = size.height

            // Define the path for the bubble with a rounded tail on the bottom left
            addPath(
                Path().apply {
                    // Start at the top-left corner
                    moveTo(cornerRadius, 0f)

                    // Top-left corner (rounded)
                    arcTo(
                        rect = Rect(0f, 0f, cornerRadius , cornerRadius ),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )

                    // Top-right corner
                    lineTo(bubbleWidth - cornerRadius, 0f)
                    arcTo(
                        rect = Rect(bubbleWidth - cornerRadius , 0f, bubbleWidth, cornerRadius),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )

                    // Bottom-right corner
                    lineTo(bubbleWidth, bubbleHeight - cornerRadius)
                    arcTo(
                        rect = Rect(bubbleWidth - cornerRadius , bubbleHeight - cornerRadius , bubbleWidth, bubbleHeight),
                        startAngleDegrees = 0f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )

                    // Bottom-left corner with the tail
                    lineTo(cornerRadius, bubbleHeight)

                    // Draw the tail
                    lineTo(tailHeight, bubbleHeight - tailHeight) // Create the tail at the bottom left

                    // Continue the bottom-left corner (rounded) after the tail
                    lineTo(cornerRadius, bubbleHeight - cornerRadius)
                    arcTo(
                        rect = Rect(0f, bubbleHeight - cornerRadius , cornerRadius , bubbleHeight),
                        startAngleDegrees = 90f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                }
            )
        }
    }
}
