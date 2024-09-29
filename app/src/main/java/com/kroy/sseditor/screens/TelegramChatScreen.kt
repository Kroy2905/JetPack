package com.kroy.sseditor.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

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
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.kroy.ssediotor.R
import com.kroy.sseditor.models.ChatMessage
import com.kroy.sseditor.ui.theme.BottomIconTint
import com.kroy.sseditor.ui.theme.CustomBoldFontFamily
import com.kroy.sseditor.ui.theme.CustomBoldTypography
import com.kroy.sseditor.ui.theme.CustomFontFamily
import com.kroy.sseditor.ui.theme.CustomMediumTypography
import com.kroy.sseditor.ui.theme.CustomRegularTypography
import com.kroy.sseditor.ui.theme.CustomTypography
import com.kroy.sseditor.ui.theme.Telegram
import com.kroy.sseditor.utils.BubbleShape
import com.kroy.sseditor.utils.Utils.generateRandomTime
import com.kroy.sseditor.utils.Utils.getBitmapFromResource
import com.kroy.sseditor.utils.Utils.parseTimeString
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import kotlin.random.Random


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomTelegramLayout(
    contactName:String,
    contactPic:Bitmap?,
    messages: List<ChatMessage>,
    initialTimeString: String, // Time in "hh:mm a" format, e.g., "12:48 AM"
    backgroundBitmap: Bitmap?,
    senderImage: Bitmap?,
    userReplySticker: Bitmap?
) {
    // Parse the initial time string to LocalTime
    Log.d("time set->","TES $initialTimeString")
    val initialTime = remember {
        parseTimeString(initialTimeString)
    }
    Log.d("time set->","parse $initialTimeString")

    // Generate a random time between 20 to 30 minutes from the initial time
    val randomStickerTime = remember { generateRandomTime(initialTime, 20, 30) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1E2E))
    ) {
        Image(
            bitmap = backgroundBitmap!!.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .blur(10.dp, 10.dp)
                .fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top bar with the formatted initial time
            CustomTopBar(
                time = randomStickerTime.format(DateTimeFormatter.ofPattern("hh:mm")),
                contactName = contactName,
                contactPic = contactPic
            )

            // LazyColumn that fills available space and aligns to the ChatBoxInput
            LazyColumn(
                reverseLayout = true,
                modifier = Modifier
                    .weight(1f) // This makes the LazyColumn take up the available space
                    .padding(horizontal = 8.dp) // Only horizontal padding to keep alignment with ChatBoxInput
            ) {

                // Render Receiver's Sticker Message with random time
                item {

                    ReceiverStickerMessage(
                        time = randomStickerTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                        userReplySticker= userReplySticker
                    )

                }
                // Render dynamic Chat Messages
                items(messages) { message ->
                    ChatBubble(
                        message = message.message,
                        time = message.timestamp,
                        isSender = message.isSender
                    )
                    Spacer(modifier = Modifier.height(3.dp)) // Space between message and time

                }
                // Render Receiver's Screenshot Message
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    ReceiverImageMessage(
                        time = initialTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                        senderImage = senderImage
                    )

                }




            }

            // Chat Input Box aligned with the bottom of the screen
            ChatBoxInput(
                 // Match padding to LazyColumn for alignment
            )
        }

    }
    }


// Function to parse the time string ("hh:mm a" format) into LocalTime


@Composable
fun ChatBubble(
    message: String,
    time: String,
    isSender: Boolean,
    isLastMessage: Boolean = false // Flag to determine if this is the last bubble
) {
    Row(
        modifier = Modifier


            .fillMaxWidth(.9f)
            .heightIn(30.dp, 60.dp)
            .padding(start = 5.dp, bottom = 3.dp, end = 10.dp),
        horizontalArrangement = if (isSender) Arrangement.Start else Arrangement.End
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.72f),
                            Color.Black.copy(alpha = 0.7f)
                        )
                    ),
                    shape = if (isLastMessage) {
                        BubbleShape(
                            tailSize = 10.dp,
                            isSender = isSender
                        ) // Use custom shape for last message
                    } else {
                        RoundedCornerShape(
                            topStart = 8.dp, topEnd = 32.dp, bottomEnd = 32.dp, bottomStart = 8.dp
                        )
                    }
                )
                .graphicsLayer {
                    shape = RoundedCornerShape(0.dp) // Keeping it rectangular
                    clip = true // Clip to bounds
                }
                .wrapContentSize()
                .padding(start = 6.dp, top = 5.dp, end = 12.dp, bottom = 5.dp)
                .wrapContentWidth(Alignment.Start) // Allow bubble to wrap content

        ) {
            // Row to display message and time on the same line

                Text(
                    text = message,
                    style = CustomRegularTypography.titleMedium,
                    fontWeight = FontWeight.W400,
                    color = Color.White,
                    fontSize = 18.sp,
                    maxLines = 3, // Limit lines if necessary
                    overflow = TextOverflow.Ellipsis // Handle overflow gracefully
                )
            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = time,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.Bottom) // Align time to the bottom of the row
            )
        }




    }
}


@Composable
fun ChatBoxInput() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black.copy(alpha = 0.7f), Color.Black.copy(alpha = 0.85f))
                )
            )
            .graphicsLayer {
                //shadowElevation = 8.dp.toPx() // Adding shadow for a blurred effect
                shape = RoundedCornerShape(0.dp) // Keeping it rectangular
                clip = true // Clip to bounds
            }
    ) {
        Row(
            modifier = Modifier

                .fillMaxWidth()
                .padding(8.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            // Attachment file icon at the start

                Icon(
                    painter = painterResource(id = com.kroy.ssediotor.R.drawable.ic_attach_file), // Replace with your actual resource
                    contentDescription = "Attach",
                    modifier = Modifier

                        .padding(end = 10.dp, start = 10.dp)
                        .size(23.dp)
                    ,


                   tint = BottomIconTint
                )


            // Box containing "Message" text and the attach sticker icon
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFF060606), RoundedCornerShape(20.dp))
                    .heightIn(min = 20.dp) // Set a minimum height to make the box thinner
                    .padding(vertical = 6.dp), // Reduce the padding to make the box thinner
                contentAlignment = Alignment.CenterStart
            ) {
                // "Message" placeholder text
                Text(
                    text = "Message",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier

                        .padding(start = 16.dp)
                )

                // Attach sticker icon aligned to the right inside the box

                    Icon(
                        painter = painterResource(id = com.kroy.ssediotor.R.drawable.ic_sticker), // Replace with your sticker icon
                        contentDescription = "Attach Sticker",
                        tint = BottomIconTint,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .align(Alignment.CenterEnd)

                    )

            }


                Icon(
                    painter = painterResource(id = com.kroy.ssediotor.R.drawable.ic_mic), // Replace with your mic icon
                    contentDescription = "Mic",
                    tint = BottomIconTint,
                    modifier = Modifier


                        .padding(end = 10.dp, start = 10.dp)
                        .size(23.dp)
                )

            // White line below the UI with top padding

        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(.4f)
                .padding(top = 20.dp)
                .height(5.dp)
                .background(Color.White)
        )
    }




}





@Composable
fun CustomTopBar(time: String,contactName: String,contactPic: Bitmap?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Black.copy(alpha = 0.85f))
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
                text = time,
                color = Color.White,
                fontSize = 18.sp,
                style = CustomBoldTypography.titleMedium,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(start = 25.dp)
            )
            Spacer(modifier = Modifier
                .width(25.dp))

            // Telegram Logo and Title in Rounded Box
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f)
                    .background(Telegram, shape = RoundedCornerShape(14.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(90.dp)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_telegram_logo),
                        contentDescription = "Telegram Logo",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(bottom = 2.dp)

                            .rotate(-50f)
                            .size(15.dp)
                    )
                    Text(
                        text = "TELEGRAM",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp,top = 2.dp)
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
                        .padding(top = 4.dp)){
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(Telegram, shape = RoundedCornerShape(12.dp))
                                .padding(start = 8.dp, end = 8.dp, bottom = 1.dp, top = 2.dp)
                        ) {
                            Text(
                                text = "${Random.nextInt(600, 900 + 1)}", // Example message count
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
                        text = contactName,
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
                    bitmap = contactPic!!.asImageBitmap(),
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
fun ReceiverImageMessage(time: String,senderImage: Bitmap?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .width(230.dp)
                .heightIn(min = 200.dp, max = 350.dp)
                .padding(2.dp)
                .clip(RoundedCornerShape(16.dp)) // Rounded corners
                .background(Color.Gray) // Optional background color
        ) {
            // Main image
            Image(
                bitmap = senderImage!!.asImageBitmap(), // Set your actual screenshot resource
                contentDescription = "Chart Screenshot",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds // Use Crop to maintain aspect ratio
            )

            // Time text
            Text(
                text = time,
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
                            Color.Black.copy(alpha = 0.35f), // Very light version of background
                            Color.Black.copy(alpha = 0.35f), // Very light version of background

                        ),
                        radius = 60f
                    )
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_forward), // Replace with your forward icon resource
                contentDescription = "Telegram Forward",
                modifier = Modifier
                    .size(20.dp) // Adjust size for the forward icon
                    .align(Alignment.Center) // Center the forward icon
                    .background(Color.Transparent),
                colorFilter = ColorFilter.tint(Color.White) // Make forward icon white
            )
        }
    }
}






@Composable
fun ReceiverStickerMessage(time: String,userReplySticker: Bitmap?) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .heightIn(min = 100.dp, max = 210.dp)
                .padding(2.dp)
        ) {
            Image(
                bitmap = userReplySticker!!.asImageBitmap(), // Set your actual sticker image
                contentDescription = "Sure Shot Sticker",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .background(Color(0x45000000), RoundedCornerShape(10.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = time,
                    color = Color.White,
                    fontSize = 12.sp,
                )

                // Single tick icon
                Icon(
                    painter = painterResource(id = R.drawable.ic_single_tick), // Replace with your single tick icon resource
                    contentDescription = "Single Tick",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(12.dp) // Adjust size as needed
                )
            }
        }
    }
}
