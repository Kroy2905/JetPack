package com.kroy.sseditor.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.kroy.ssediotor.R

@Composable
fun TelegramLayout() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1E2E)) // Background color similar to Telegram
    ) {
        // Fake Status Bar + Custom Top Bar
        FakeStatusBarWithTitle()

        // Content: Screenshot and chat messages
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Image Screenshot (chart) - Receiver's reply (right-aligned)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = com.kroy.ssediotor.R.drawable.f), // Replace with actual image resource
                    contentDescription = "Chart Screenshot",
                    modifier = Modifier
                        .width(250.dp)
                        .heightIn(min = 200.dp, max = 300.dp) // Limit the image size
                        .padding(8.dp),
                    contentScale = ContentScale.FillWidth
                )
            }

            // Sender's messages (left-aligned)
            ChatBubble(
                message = "Very deep candle",
                time = "12:51 AM",
                isSender = true // Sender's message will be left-aligned
            )
            ChatBubble(
                message = "Amazing 😍😍",
                time = "12:51 AM",
                isSender = true
            )
            ChatBubble(
                message = "Loved it ma'am 😅",
                time = "12:52 AM",
                isSender = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom animated image (Sticker) - Receiver's reply (right-aligned)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = com.kroy.ssediotor.R.drawable.f), // Replace with actual sticker image
                    contentDescription = "Sure Shot Sticker",
                    modifier = Modifier
                        .width(150.dp)
                        .heightIn(min = 100.dp, max = 150.dp) // Limit the size of the bottom image
                        .padding(8.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }

        // Chatbox with attachment icon
        ChatBoxInput()
    }
}

@Composable
fun FakeStatusBarWithTitle() {
    Column {
        // Fake Status Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(4.dp)
                .height(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "12:52", color = Color.White, fontSize = 12.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(id = com.kroy.ssediotor.R.drawable.ic_signal), contentDescription = "Signal", tint = Color.White, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Icon(painterResource(id = com.kroy.ssediotor.R.drawable.ic_wifi), contentDescription = "Wi-Fi", tint = Color.White, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "86%", color = Color.White, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(2.dp))
                Icon(painterResource(id = com.kroy.ssediotor.R.drawable.ic_battery), contentDescription = "Battery", tint = Color.White, modifier = Modifier.size(12.dp))
            }
        }

        // Custom Title Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0088CC))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back icon
            IconButton(onClick = { /* Handle back */ }) {
                Icon(painterResource(id = com.kroy.ssediotor.R.drawable.ic_back), contentDescription = "Back", tint = Color.White)
            }

            // Title Text
            Text(
                text = "Fake Id",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            // Profile icon on the right
            Icon(
                painter = painterResource(id = com.kroy.ssediotor.R.drawable.logo),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.White, CircleShape), // Circle-shaped background for the profile icon
                tint = Color.Unspecified // Use the default colors of the profile image
            )
        }
    }
}

@Composable
fun ChatBubble(message: String, time: String, isSender: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (isSender) Arrangement.Start else Arrangement.End // Align based on sender or receiver
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF2D2F33), shape = RoundedCornerShape(32.dp))
                .padding(7.dp)
                .widthIn(max = 250.dp) // Limit the message bubble width
        ) {
            // Row to display message and time on the same line
            Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.Start // Ensure the time stays at the end
            ) {
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 18.sp,
                   // modifier = Modifier.weight(1f) // Let the message take up remaining space
                )
                Spacer(modifier = Modifier.width(8.dp)) // Space between message and time
                Text(
                    text = time,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Bottom) // Align time to the bottom of the row
                )
            }
        }
    }
}

@Composable
fun ChatBoxInput() {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFF2D2F33), RoundedCornerShape(40.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Attachment icon
        IconButton(onClick = { /* Handle attachment */ }) {
            Icon(painterResource(id = com.kroy.ssediotor.R.drawable.ic_attach), contentDescription = "Attach", tint = Color.White)
        }

        // Text input field
        BasicTextField(
            value = textState,
            onValueChange = { textState = it },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.White)
        )

        // Send icon
        IconButton(onClick = { /* Handle send */ }) {
            Icon(painterResource(id = com.kroy.ssediotor.R.drawable.c), contentDescription = "Send", tint = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelegramLayout() {
    TelegramLayout()
}