package com.kroy.sseditor.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
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
            painter = painterResource(id = R.drawable.a), // Set your actual background image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .blur(10.dp, 10.dp)
                .fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CustomTopBar()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                // Receiver's Screenshot Message
                ReceiverImageMessage()

                // Sender's Messages
                ChatBubble("Very deep candle", "12:51 AM", isSender = true)
                ChatBubble("Amazing 😍😍", "12:51 AM", isSender = true)
                ChatBubble("Loved it ma'am 😅", "12:52 AM", isSender = true)

                // Receiver's Sticker Message
                Spacer(modifier = Modifier.height(5.dp))
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
            .padding(start = 5.dp, bottom = 3.dp),
        horizontalArrangement = if (isSender) Arrangement.Start else Arrangement.End // Align based on sender or receiver
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF2D2F33), shape = RoundedCornerShape(32.dp))
                .padding(2.dp)
                .widthIn(max = 250.dp) // Limit the message bubble width
        ) {
            // Row to display message and time on the same line
            Row(
                modifier = Modifier
                    .padding(6.dp, 4.dp, 10.dp, 4.dp)
                    .wrapContentSize(),
                horizontalArrangement = Arrangement.Start // Ensure the time stays at the end
            ) {
                Text(
                    text = message,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.width(8.dp)) // Space between message and time
                Text(
                    text = time,
                    fontWeight = FontWeight.ExtraBold,
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Black.copy(alpha = 0.6f))
                )
            )
            .graphicsLayer {
                //shadowElevation = 8.dp.toPx() // Adding shadow for a blurred effect
                shape = RoundedCornerShape(0.dp) // Keeping it rectangular
                clip = true // Clip to bounds
            }
    ){
        // Top Status Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()

                .padding(8.dp)
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Time
            Text(
                text = "12:52",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 25.dp)
            )
            Spacer(modifier = Modifier
                .width(18.dp))

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
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            // Status Icons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 20.dp,)
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
     //2nd line of status bar
        Row(
            modifier = Modifier
                .fillMaxWidth()

                .padding(end = 15.dp)
                .wrapContentHeight(),

        ){

            Box(
                modifier = Modifier
                    .fillMaxWidth()

                    .height(60.dp)
            ) {

                    // Back Button
                Row(modifier = Modifier
                    .padding(top=16.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        modifier = Modifier
                            .size(30.dp)
                        ,
                        contentDescription = "Back",
                        tint = Telegram // Ensure Telegram color is defined
                    )


                    // Pending Messages Box
                    Box(modifier = Modifier
                        .padding(top = 5.dp)){
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(Telegram, shape = RoundedCornerShape(12.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "1141", // Example message count
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                }
                // Spacer to create space between the Row and Column
                Spacer(modifier = Modifier
                    .height(16.dp)
                    .align(Alignment.Center)
                    .width(5.dp)) // Adjust the height as needed





                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Fake Id",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Text(
                        text = "last recently seen",
                        color = Color(0xFFD1E3FF),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 2.dp,start = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }

                // Profile Picture Positioning
                Image(
                    painter = painterResource(id = R.drawable.e),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF40BAF7), CircleShape)
                        .clip(CircleShape) // Clip the image to a circular shape
                        .align(Alignment.CenterEnd),
                    contentScale = ContentScale.Crop // Ensures the image fills the bounds of the circular shape
                )


            }
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
                .width(230.dp)
                .heightIn(min = 200.dp, max = 400.dp)
                .padding(2.dp)

                .clip(RoundedCornerShape(16.dp)) // Rounded corners
                .background(Color.Gray) // Optional background color
        ) {
            // Main image
            Image(
                painter = painterResource(id = R.drawable.f), // Set your actual screenshot resource
                contentDescription = "Chart Screenshot",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Use Crop to maintain aspect ratio
            )

            // Time text
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

        // Forward icon placed outside the chart box
        Box(
            modifier = Modifier
                .align(Alignment.Bottom) // Align to the bottom of the row
                .padding(start = 4.dp, bottom = 4.dp)
                .size(36.dp) // Circular size for the forward icon background
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.3f), // Very light version of background
                            Color.Transparent
                        ),
                        radius = 60f
                    )
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_forward), // Replace with your forward icon resource
                contentDescription = "Telegram Forward",
                modifier = Modifier
                    .size(18.dp) // Adjust size for the forward icon
                    .align(Alignment.Center) // Center the forward icon
                    .background(Color.Transparent),
                colorFilter = ColorFilter.tint(Color.White) // Make forward icon white
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
                .padding(2.dp)
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

