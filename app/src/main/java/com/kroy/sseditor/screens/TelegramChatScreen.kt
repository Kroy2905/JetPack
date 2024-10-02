package com.kroy.sseditor.screens

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.kroy.ssediotor.R
import com.kroy.sseditor.models.ChatMessage
import com.kroy.sseditor.ui.theme.BottomIconTint
import com.kroy.sseditor.ui.theme.CustomBoldTypography
import com.kroy.sseditor.ui.theme.CustomRegularTypography
import com.kroy.sseditor.ui.theme.Telegram
import com.kroy.sseditor.utils.Utils.convertLettersToUppercase
import com.kroy.sseditor.utils.Utils.generateRandomTime
import com.kroy.sseditor.utils.Utils.getBitmapFromResource
import com.kroy.sseditor.utils.Utils.parseTimeString
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun previewTelegram(){
    val context = LocalContext.current
    val sampleMessages = listOf(
        ChatMessage("11111111111 12321312 2131 213  321213 ram shyam ", "12:50 AM", isSender = true),
        ChatMessage("Hello you are a wonderful person , we can do a lot of stuff ", "12:50 AM", isSender = true),
        ChatMessage("How 435943594", "12:50 AM", isSender = true)
    )

    // Replace with actual Bitmap objects for testing
    val contactPic: Bitmap? = getBitmapFromResource(context,R.drawable.f)
    val backgroundBitmap: Bitmap? = getBitmapFromResource(context,R.drawable.telegram_bg)
    val senderImage: Bitmap? = getBitmapFromResource(context,R.drawable.b)
    val userReplySticker: Bitmap? = getBitmapFromResource(context,R.drawable.d)

    CustomTelegramLayout(
        contactName ="Random Name",
        contactPic = contactPic,
        messages = sampleMessages,
        initialTimeString = "12:48 AM",
        backgroundBitmap = backgroundBitmap,
        senderImage = senderImage,
        userReplySticker = userReplySticker
    )
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomTelegramLayout(
    contactName: String,
    contactPic: Bitmap?,
    messages: List<ChatMessage>,
    initialTimeString: String, // Time in "hh:mm a" format, e.g., "12:48 AM"
    backgroundBitmap: Bitmap?,
    senderImage: Bitmap?,
    userReplySticker: Bitmap?
) {
    // Parse the initial time string to LocalTime
    Log.d("time set->", "TES $initialTimeString")
    val initialTime = remember {
        parseTimeString(initialTimeString)
    }
    val randomInitialTime = remember { generateRandomTime(initialTime, -15, 15) }
    Log.d("time set->", "parse $initialTimeString")

    // Generate a random time between 20 to 30 minutes from the initial time
    val randomStickerTime = remember { generateRandomTime(randomInitialTime, 20, 30) }

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

            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 8.dp) // Only horizontal padding to keep alignment with ChatBoxInput
                ) {
                    // Render Receiver's Screenshot Message
                    item {
                        Spacer(modifier = Modifier.height(3.dp))
                        ReceiverImageMessage(
                            time = randomInitialTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                            senderImage = senderImage
                        )
                    }

                    // Render dynamic Chat Messages
                    itemsIndexed(messages) { index, message ->


                        // Check if this is the last message in the list
                        val isLastMessage = index == messages.size - 1

                        ChatBubble(
                            message = message.message,
                            time = randomInitialTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                            isSender = message.isSender,
                            isLastMessage = isLastMessage // Only true for the last message
                        )
                    }

                    // Render Receiver's Sticker Message with random time
                    item {
                        ReceiverStickerMessage(
                            time = randomStickerTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                            userReplySticker = userReplySticker
                        )
                    }
                }

                // "Today" text at the top
                Text(
                    text = "Today",
                    color = Color.White,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 6.dp) // Adjust the top padding for gap
                        .background(Color(0x65000000), RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                )
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
    isLastMessage: Boolean
) {
    // Combine message and timer strings
    val formattedTime = convertLettersToUppercase(time)
    val combinedText = "$message  $formattedTime"

    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f) // The Row still fills the available width
            .padding(bottom = if (isLastMessage) 6.dp else 2.dp) // Adjust padding for the last message
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 2.dp) // Adjust start padding
        ) {
            // Main chat bubble
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.72f),
                                Color.Black.copy(alpha = 0.7f)
                            )
                        ),
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 16.dp,
                            bottomEnd = 16.dp,
                            bottomStart = if (isLastMessage) 8.dp else 8.dp // Adjust based on message position
                        )
                    )
                    .padding(horizontal = 10.dp, vertical = 8.dp)
                    .wrapContentWidth() // The box wraps around its content
            ) {
                // Determine the total length
                val messageLength = message.length
                val timeLength = formattedTime.length
                val spaceLength = 2 // Length of the space between message and timer
                val combinedLength = messageLength + timeLength + spaceLength

                // If the combined length exceeds 25 characters, adjust layout
                if (combinedLength > 45) {
                    val allowedMessageLength =  (timeLength + spaceLength)
                    val displayMessage = if (allowedMessageLength < messageLength) {
                       // message.take(allowedMessageLength) // Take only part of the message
                        message
                    } else {
                        message // If it fits, display the whole message
                    }

                    // Display the message and timer in a Column
                    Column {
                        // Display the trimmed message
                        Text(
                            text = displayMessage,
                            style = CustomRegularTypography.titleMedium,
                            fontWeight = FontWeight.W400,
                            color = Color.White,
                            fontSize = 15.sp,
                            maxLines = Int.MAX_VALUE,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.wrapContentWidth()
                        )

                        // Timer text aligned at the end of the box
                        Text(
                            text = formattedTime,
                            style = CustomRegularTypography.titleMedium,
                            fontWeight = FontWeight.W400,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier
                                .align(Alignment.End) // Align timer at the end of the box
                                .padding(top = 2.dp) // Optional: add padding above the timer
                        )
                    }
                } else {
                    // If the combined length is less than or equal to 25 characters
                    Text(
                        text = buildAnnotatedString {
                            append(message)
                            append("   ") // Add space between message and time
                            withStyle(style = SpanStyle(fontSize = 12.sp, color = Color.Gray)) {
                                append(formattedTime)
                            }
                        },
                        style = CustomRegularTypography.titleMedium,
                        fontWeight = FontWeight.W400,
                        color = Color.White,
                        fontSize = 15.sp,
                        maxLines = Int.MAX_VALUE,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth() // Box adjusts based on content
                    )
                }
            }

            // Show triangle only if this is the last message
            if (isLastMessage) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart) // Position the triangle at the bottom start
                        .offset(x = (-10).dp) // Slight offset for triangle size
                ) {
                    TriangleShape(color = Color.Black.copy(alpha = 0.58f)) // Triangle at the bottom start for last message
                }
            }
        }
    }
}
















@Composable
fun TriangleShape(color: Color) {
    Canvas(modifier = Modifier
        .padding(bottom = 2.dp)
        .rotate(270f) // Rotate the triangle
        .size(10.dp)) {
        val path = Path().apply {
            // Start from the bottom left
            moveTo(-10f, size.height * 0.2f)

            // Draw a straight line to the bottom right
            lineTo(size.width * 1.4f, size.height * 1.3f)

            // Create a quadratic Bezier curve for the side that becomes the top after rotation
            quadraticBezierTo(
                size.width / 2f, size.height * 0.2f,  // Control point for the curve
                size.width / 2f, size.height * 2.4f  // End point of the curve
            )

            // Close the path
            close()
        }

        // Draw the triangle with the curved side
        drawPath(path = path, color = color)
    }
}




@Composable
fun ChatBoxInput() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.87f),
                        Color.Black.copy(alpha = 0.87f)
                    )
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
                    colors = listOf(Color.Black.copy(alpha = 0.9f), Color.Black.copy(alpha = 0.9f))
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
                fontSize = 16.sp,
                style = CustomBoldTypography.titleMedium,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(start = 25.dp)
            )
            Spacer(modifier = Modifier
                .width(25.dp))

            // Telegram Logo and Title in Rounded Box
//            Box(
//                modifier = Modifier
//                    .wrapContentSize()
//                    .weight(1f)
//                    .background(Telegram, shape = RoundedCornerShape(14.dp))
//                    .padding(horizontal = 10.dp, vertical = 4.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center,
//                    modifier = Modifier
//                        .width(90.dp)
//
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_telegram_logo),
//                        contentDescription = "Telegram Logo",
//                        tint = Color.White,
//                        modifier = Modifier
//                            .padding(bottom = 2.dp)
//
//                            .rotate(-50f)
//                            .size(15.dp)
//                    )
//                    Text(
//                        text = "TELEGRAM",
//                        color = Color.White,
//                        fontSize = 13.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(start = 4.dp,top = 2.dp)
//                    )
//                }
//            }

            // Status Icons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp,)
            ) {
                Icon(painterResource(id = R.drawable.ic_signal2), contentDescription = "Signal", tint = Color.White, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Icon(painterResource(id = R.drawable.ic_wifi), contentDescription = "Wi-Fi", tint = Color.White, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painter = painterResource(id = R.drawable.battery4),
                    contentDescription = "Battery",
                    tint = Color.White,
                    modifier = Modifier
                        .size(25.dp)
                        .rotate(0f)
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
                    .padding(top=18.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        modifier = Modifier
                            .size(27.dp)
                        ,
                        contentDescription = "Back",
                        tint = Telegram // Ensure Telegram color is defined
                    )


                    // Pending Messages Box
                    Box(modifier = Modifier
                        .padding(top = 6.dp)){
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(Telegram, shape = RoundedCornerShape(12.dp))
                                .padding(start = 8.dp, end = 8.dp, bottom = 2.dp, top = 2.dp)
                        ) {
                            Text(
                                text = "${Random.nextInt(1000, 2000 + 1)}", // Example message count
                                color = Color.White,
                                fontSize = 10.sp,
                                style = CustomRegularTypography.titleMedium
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
                        text = "last seen recently",
                        color = Color(0xFFD1E3FF),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 2.dp,start = 12.dp),
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
                .widthIn(100.dp, 230.dp)
                .heightIn(min = 150.dp, max = 320.dp)
                .padding(start = 1.dp, bottom = 3.dp)
                .clip(RoundedCornerShape(16.dp)) // Clip to rounded corners
                .border(
                    width = (0.6f).dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp) // Apply same rounded shape to border
                ) // Border with rounded corners

        ) {
            // Main image
            Image(
                bitmap = senderImage!!.asImageBitmap(), // Set your actual screenshot resource
                contentDescription = "Chart Screenshot",
                modifier = Modifier
                    .fillMaxSize(),

                contentScale = ContentScale.FillBounds // Use Crop to maintain aspect ratio
            )

            // Time text
            Text(
                text = convertLettersToUppercase(time) ,
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
                .widthIn(150.dp, 200.dp)
                .heightIn(min = 100.dp, max = 210.dp)
                .padding(2.dp)
        ) {
            Image(
                bitmap = userReplySticker!!.asImageBitmap(), // Set your actual sticker image
                contentDescription = "Sure Shot Sticker",
                modifier = Modifier.wrapContentSize(),
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
                    text = convertLettersToUppercase(time) ,
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


