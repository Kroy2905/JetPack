package com.kroy.sseditor.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kroy.ssediotor.R
import com.kroy.sseditor.models.ChatItem
import com.kroy.sseditor.ui.theme.CustomBoldTypography
import com.kroy.sseditor.ui.theme.CustomRobotoMediumFontFamily
import com.kroy.sseditor.ui.theme.Dimens
import com.kroy.sseditor.ui.theme.Telegram
import com.kroy.sseditor.ui.theme.TelegramDark
import com.kroy.sseditor.utils.SelectedClient
import com.kroy.sseditor.utils.Utils
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(chats:List<ChatItem>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Status Bar
            StatusBar()

            // Chat List (middle content)
            ChatListUI(
                modifier = Modifier
                    .weight(1f)
                ,// Fills the remaining space right after the status bar,
                chats

            )

            // Bottom Navigation Bar (fixed at the bottom)
            BottomNavBar(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(

                                Color.LightGray.copy(alpha = 0.1f),
                                Color.LightGray.copy(alpha = 0.1f),

                                )
                        )
                    )
                    .fillMaxWidth()
                //.align(Alignment.BottomCenter)

            )
        }

//        // Bottom Navigation Bar (fixed at the bottom)
//        BottomNavBar(
//            modifier = Modifier
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//
//                            Color.LightGray.copy(alpha = 0.1f),
//                            Color.LightGray.copy(alpha = 0.1f),
//
//                            )
//                    )
//                )
//                .fillMaxWidth()
//                //.align(Alignment.BottomCenter)
//
//        )
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatusBar() {
    val initialTime = remember {
        Utils.parseTimeString(SelectedClient.time)
    }
    Log.d("time set->", "parse $initialTime")
    val randomInitialTime = remember { Utils.generateRandomTime(initialTime, -25, 25) }
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(

                        Color.LightGray.copy(alpha = 0.1f),
                        Color.LightGray.copy(alpha = 0.1f),

                        )
                )
            )
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp) // Padding only at the sides and top
    ) {
        // First Row - Status (Time, Signal, Battery)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Time
            Text(
                text = Utils.removeLeadingZero(randomInitialTime.format(DateTimeFormatter.ofPattern("hh:mm"))),
                color = Color.White,
                fontSize = 12.sp,
                style = CustomBoldTypography.titleMedium,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(start = 25.dp)
            )
            Spacer(modifier = Modifier.width(25.dp))

            // Status Icons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp)
            ) {
                Icon(painterResource(id = R.drawable.ic_signal2), contentDescription = "Signal", tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Icon(painterResource(id = R.drawable.ic_wifi), contentDescription = "Wi-Fi", tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painter = painterResource(id = R.drawable.battery4),
                    contentDescription = "Battery",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(0f)
                )
            }
        }

        // Second Row - Telegram Title & Unread Tabs
        Row(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth()
                .padding(top = 8.dp) // Only top padding between first and second row
        ) {
            Text(
                text = "Edit",
                color = TelegramDark, // Assuming you have defined TelegramDark
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Chats",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Row(
                modifier = Modifier.align(Alignment.CenterVertically),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_dotted),
                    contentDescription = "Search",
                    tint = TelegramDark,
                    modifier = Modifier
                        .rotate(180f)
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "More options",
                    tint = TelegramDark,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(16.dp)
                )
            }
        }

        // Third Row - Tabs with "Unread" centered vertically
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),  // Only top padding between first and second row
            verticalAlignment = Alignment.CenterVertically, // Center content vertically in the row
            horizontalArrangement = Arrangement.Start // Spread the content across the width of the row
        ) {
            val spacerValue = 12.dp
            Spacer(modifier = Modifier.width(spacerValue))
            // All tab
            Text(
                text = "All",
                color = Color.Gray,
                fontSize = Dimens.ChatScreenCategoryTextSize,
                fontFamily = CustomRobotoMediumFontFamily,
                modifier = Modifier.padding(end = 18.dp)
            )
            Spacer(modifier = Modifier.width(spacerValue))




            // Personal tab
            Row(
                modifier = Modifier
                    .padding(end = 5.dp)
            ) {
                Text(
                    text = "Personal",
                    color = Color.Gray,
                    fontSize = Dimens.ChatScreenCategoryTextSize,
                    fontFamily = CustomRobotoMediumFontFamily,
                    modifier = Modifier.padding(end = 3.dp)
                )
                BadgeBoxSmall(Random.nextInt(2, 10), 15)
            }
            Spacer(modifier = Modifier.width(spacerValue))


            // "Unread" tab at the center, vertically and horizontally
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // Center content horizontally in the column
                verticalArrangement = Arrangement.Center // Center content vertically in the column
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 2.dp)
                ) {
                    Text(
                        text = "Unread",
                        color = Telegram,
                        fontSize = Dimens.ChatScreenCategoryTextSize,
                        fontFamily = CustomRobotoMediumFontFamily,
                        modifier = Modifier.padding(end = 3.dp)
                    )
                    BadgeBoxSmall(Random.nextInt(2, 10), 15)
                }
                Divider(
                    color = Telegram,
                    thickness = 2.dp,
                    modifier = Modifier
                        .width(80.dp)  // Adjust width based on content
                        .padding(top = 4.dp, end = 19.dp)
                )
            }
            Spacer(modifier = Modifier.width(spacerValue))


            // Channels tab
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "Channels",
                    color = Color.Gray,
                    fontSize = Dimens.ChatScreenCategoryTextSize,
                    fontFamily = CustomRobotoMediumFontFamily,
                    modifier = Modifier.padding(end = 3.dp)
                )
                BadgeBoxSmall(Random.nextInt(2, 10), 15)
            }
        }
    }
}





@Composable
fun BottomNavBar(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(top = 4.dp)

                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,

            ) {
            // Contacts column
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_contacts),
                    contentDescription = "Contacts",
                    tint = Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    text = "Contacts",
                    color = Color.Gray,
                    fontSize = Dimens.ChatScreenBottomBarTextSize,
                    fontFamily = CustomRobotoMediumFontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Chats column with badge overlay
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_pending_msg_white),
                        contentDescription = "Pending messages",
                        tint = Telegram.copy(0.72f),
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = "Chats",
                        color = Telegram,
                        fontSize = Dimens.ChatScreenBottomBarTextSize,
                        fontFamily = CustomRobotoMediumFontFamily,
                        maxLines = 1,

                        )
                }

                // Badge positioned on top of the icon
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)

                        .offset(x = -1.dp, y = (0.5f).dp) // Adjust as needed for exact positioning
                        .wrapContentWidth()
                        .background(Color(0xFFE90707), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "1.${Random.nextInt(1, 4)}K",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontFamily = CustomRobotoMediumFontFamily,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            // Settings column with profile picture
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                Image(
                    painter = painterResource(id = R.drawable.b),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "Settings",
                    color = Color.Gray,
                    fontSize = Dimens.ChatScreenBottomBarTextSize,
                    fontFamily = CustomRobotoMediumFontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatListUI(modifier: Modifier = Modifier,chats: List<ChatItem>) {


    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = modifier

            .fillMaxSize()
    ) {
        items(chats) { chat ->
            ChatRow(chat)
            Divider(color = Color.Gray, thickness = 0.1.dp)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatRow(chat: ChatItem) {
    val initialTime = remember {
        Utils.parseTimeString(chat.time)
    }
    Log.d("time set->", "parse $initialTime")
    val randomInitialTime = remember { Utils.generateRandomTime(initialTime, -25, 25) }
    val formattedTime = Utils.convertLettersToUppercase(randomInitialTime.format(DateTimeFormatter.ofPattern("hh:mm a")),)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            bitmap = chat.profileImage!!.asImageBitmap(),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = chat.name,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = chat.message,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = formattedTime,
                color = Color.Gray
            )
            if (chat.unreadCount > 0) {
                BadgeBox(chat.unreadCount,24)
            }
        }
    }
}

@Composable
fun BadgeBox(unreadCount: Int,size:Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size.dp)
            .background(Telegram, CircleShape)
    ) {
        Text(text = unreadCount.toString(), color = Color.White)
    }
}

@Composable
fun BadgeBoxSmall(unreadCount: Int,size:Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(top = 4.dp, end = 18.dp)
            .size(size.dp)
            .background(Telegram, CircleShape)
    ) {
        Text(text = unreadCount.toString(), color = Color.White , fontSize = 12.sp)
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3200px,dpi=800" // This simulates a 6.7-inch screen with 1440x3200 resolution and 560 dpi
)
@Composable
fun TelegramScreenPreview() {
    val context = LocalContext.current
    val chats = listOf(
        ChatItem("Animesh Mondal", "Hi", SelectedClient.time,Utils.getBitmapFromResource(context,R.drawable.b) , Random.nextInt(2, 10)),
        ChatItem("VS", "Hey", SelectedClient.time, Utils.getBitmapFromResource(context,R.drawable.b), Random.nextInt(2, 10)),
        ChatItem("Siam", "I kiss your neck alsoo", SelectedClient.time, Utils.getBitmapFromResource(context,R.drawable.a), Random.nextInt(2, 10)),
        ChatItem("EXCEPTION", "Hey", SelectedClient.time,Utils.getBitmapFromResource(context,R.drawable.f), Random.nextInt(2, 10)),
        ChatItem("Jsvindr Sng", "https://t.me/+i_voE00fHsMOODA9", SelectedClient.time,Utils.getBitmapFromResource(context,R.drawable.e), Random.nextInt(2, 10)),
        ChatItem("Apple", "https://t.me/+qnGC9Zd2csJkZDU9", SelectedClient.time,Utils.getBitmapFromResource(context,R.drawable.d), Random.nextInt(2, 10)),
        ChatItem("Binary Trading Trader", "Ftgmn...", SelectedClient.time,Utils.getBitmapFromResource(context,R.drawable.a), Random.nextInt(2, 10)),
        ChatItem("Binary Trading Trader", "Ftgmn...", SelectedClient.time,Utils.getBitmapFromResource(context,R.drawable.a), Random.nextInt(2, 10)),
        ChatItem("Binary Trading Trader", "Ftgmn...", SelectedClient.time,Utils.getBitmapFromResource(context,R.drawable.a), Random.nextInt(2, 10)),
        ChatItem("Binary Trading Trader", "Ftgmn...", SelectedClient.time,Utils.getBitmapFromResource(context,R.drawable.a), Random.nextInt(2, 10)),
        ChatItem("Binary Trading Trader", "Ftgmn...", SelectedClient.time,Utils.getBitmapFromResource(context,R.drawable.a), Random.nextInt(2, 10)),
        ChatItem("Binary Trading Trader", "Ftgmn...", SelectedClient.time,Utils.getBitmapFromResource(context,R.drawable.a), Random.nextInt(2, 10)),
        ChatItem("Binary Trading Trader", "Ftgmn...", SelectedClient.time,Utils.getBitmapFromResource(context,R.drawable.a), Random.nextInt(2, 10)),
        ChatItem("Binary Trading Trader", "Ftgmn...", SelectedClient.time,Utils.getBitmapFromResource(context,R.drawable.a), Random.nextInt(2, 10)),
        ChatItem("Tronix Bot", "🦴🦴🦴🦴🦴", SelectedClient.time, Utils.getBitmapFromResource(context,R.drawable.b), Random.nextInt(2, 10))
    )
    ChatScreen(chats)
}
