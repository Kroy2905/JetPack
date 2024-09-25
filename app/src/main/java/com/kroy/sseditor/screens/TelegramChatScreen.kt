package com.kroy.sseditor.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.kroy.ssediotor.R
import com.kroy.sseditor.ui.theme.CustomBoldFontFamily
import com.kroy.sseditor.ui.theme.CustomBoldTypography
import com.kroy.sseditor.ui.theme.Telegram

@Preview
@Composable
fun previewTelegram(){
    CustomTelegramLayout()
}

@Composable
fun CustomTelegramLayout() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1E2E)) // Change to match background if needed
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg), // Set your actual background image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CustomTopBar()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                // Receiver's Screenshot Message
                ReceiverImageMessage()

                // Sender's Messages
                ChatBubble("Very deep candle", "12:51 AM", isSender = true)
                ChatBubble("Amazing 😍😍", "12:51 AM", isSender = true)
                ChatBubble("Loved it ma'am 😅", "12:52 AM", isSender = true)

                // Receiver's Sticker Message
                Spacer(modifier = Modifier.height(16.dp))
                ReceiverStickerMessage()
            }

            // Chat Input Box
            ChatBoxInput()
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




@Composable
fun CustomTopBar() {
    Column {
        // Top Status Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(8.dp)
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Time
            Text(
                text = "12:52",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 25.dp)
            )

            // Telegram Logo and Title in Rounded Box
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f)
                    .background(Telegram, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_telegram_logo),
                        contentDescription = "Telegram Logo",
                        tint = Color.White,
                        modifier = Modifier
                            .rotate(-40f)
                            .size(15.dp)
                    )
                    Text(
                        text = "TELEGRAM",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            // Status Icons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 15.dp,)
            ) {
                Icon(painterResource(id = R.drawable.ic_signal), contentDescription = "Signal", tint = Color.White, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Icon(painterResource(id = R.drawable.ic_wifi), contentDescription = "Wi-Fi", tint = Color.White, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_battery),
                    contentDescription = "Battery",
                    tint = Color.White,
                    modifier = Modifier
                        .size(25.dp)
                        .rotate(90f)
                )
            }
        }

        // Title Bar with Back Button, Pending Messages, and Profile
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .height(60.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                // Back Button
                IconButton(onClick = { /* Handle back */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                // Pending Messages Box
                Box(
                    modifier = Modifier
                        .background(Telegram, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "1141", // Example message count
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Fake Id",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "last recently seen",
                    color = Color(0xFFD1E3FF),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 2.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Profile Picture Positioning
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(32.dp)
                    .background(Color(0xFF40BAF7), CircleShape)
                    .align(Alignment.CenterEnd)
                    .padding(end = 35.dp), // Adjusted to shift left
                tint = Color.Unspecified
            )
        }
    }
}


@Composable
fun ReceiverImageMessage() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .width(250.dp)
                .heightIn(min = 200.dp, max = 300.dp)
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.f), // Set your actual screenshot resource
                contentDescription = "Chart Screenshot",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "12:51 AM",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .background(Color(0x80000000), RoundedCornerShape(10.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
fun ReceiverStickerMessage() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .heightIn(min = 100.dp, max = 200.dp)
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sticker_image), // Set your actual sticker image
                contentDescription = "Sure Shot Sticker",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "12:52 AM",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .background(Color(0x80000000), RoundedCornerShape(10.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

