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

// Define a custom bubble shape with a tail
// Custom Bubble Shape with a pointer
class BubbleShape(private val tailSize: Dp = 8.dp, private val cornerRadius: Dp = 16.dp, private val isSender: Boolean) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): androidx.compose.ui.graphics.Outline {
        val path = Path().apply {
            // Draw the bubble rectangle with rounded corners
            addRoundRect(
               RoundRect(
                    left = tailSize.value,
                    top = 0f,
                    right = size.width,
                    bottom = size.height,
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius.value)
                )
            )

            // Create the tail as a triangle
            if (isSender) {
                moveTo(0f, size.height / 2)
                lineTo(tailSize.value, size.height / 2 - tailSize.value)
                lineTo(tailSize.value, size.height / 2 + tailSize.value)
            } else {
                moveTo(size.width, size.height / 2)
                lineTo(size.width - tailSize.value, size.height / 2 - tailSize.value)
                lineTo(size.width - tailSize.value, size.height / 2 + tailSize.value)
            }
            close()
        }
        return androidx.compose.ui.graphics.Outline.Generic(path)
    }
}
@Composable
fun ChatBubbleWithTail(
    message: String,
    modifier: Modifier = Modifier,
    isSender: Boolean
) {
    Surface(
        modifier = modifier,
        color = if (isSender) Color(0xFF00897B) else Color(0xFF37474F),
        shape = BubbleShape(tailSize = 8.dp, cornerRadius = 16.dp,isSender = isSender) // Custom bubble shape
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = message,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PreviewChatBubble() {
    ChatBubbleWithTail(message = "Hello, this is a message!", isSender = false)
}
