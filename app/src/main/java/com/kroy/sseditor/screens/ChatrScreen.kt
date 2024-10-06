package com.kroy.sseditor.screens

import android.os.Build
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kroy.ssediotor.R
import com.kroy.sseditor.ui.theme.CustomBoldTypography
import com.kroy.sseditor.ui.theme.Telegram
import com.kroy.sseditor.ui.theme.TelegramDark
import kotlin.random.Random

@Composable
fun TelegramScreen() {
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
                   // .weight(1f) // Fills the remaining space right after the status bar
            )
        }

        // Bottom Navigation Bar (fixed at the bottom)
        BottomNavBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        )
    }
}

@Composable
fun StatusBar() {
    Column(
        modifier = Modifier
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
                text = "11:05",
                color = Color.White,
                fontSize = 12.sp,
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
// Second Row - Telegram Title & Unread Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp) // Only top padding between first and second row
        ) {
            // "Edit" aligned at the beginning
            Text(
                text = "Edit",
                color = TelegramDark, // Assuming you have defined TelegramDark
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            // Centered "Chats" text
            Row(
                modifier = Modifier
                    .weight(1f) // This pushes the other content to the sides, making "Chats" center-aligned
                    .align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.Center // Aligns the text centrally within the available space
            ) {
                Text(
                    text = "Chats",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }

            // Two symbols/icons aligned at the end of the row
            Row(
                modifier = Modifier.align(Alignment.CenterVertically),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // First Icon
                Icon(
                    painter = painterResource(id = R.drawable.ic_add), // Replace with your icon
                    contentDescription = "Search",
                    tint = TelegramDark,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(10.dp)) // Spacing between icons

                // Second Icon
                Icon(
                    painter = painterResource(id = R.drawable.ic_sticker), // Replace with your icon
                    contentDescription = "More options",
                    tint = TelegramDark,
                    modifier = Modifier
                        .padding(end= 5.dp)
                        .size(16.dp)
                )
            }
        }


        // Third Row - Telegram Title & Unread Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),  // Only top padding between first and second row

        ) {
            Text(
                text = "Chats",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Row {
                Text(
                    text = "All",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "Unread",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "Channels",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // White Divider Line
        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)  // Add a small spacing after the status bar content
        )
    }
}



@Composable
fun BottomNavBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Handle contacts */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_contacts),
                contentDescription = "Contacts",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color(0xFF1D9BF0), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "1.7K", color = Color.White, fontSize = 16.sp)
        }

        IconButton(onClick = { /* Handle settings */ }) {
            Image(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "Profile",
                colorFilter = ColorFilter.tint(Color.White), // Apply white tint
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
@Composable
fun ChatListUI(modifier: Modifier = Modifier) {
    val chats = listOf(
        ChatItem("Animesh Mondal", "Hi", "16/09", R.drawable.b, Random.nextInt(2, 10)),
        ChatItem("VS", "Hey", "31/08", R.drawable.b, Random.nextInt(2, 10)),
        ChatItem("Siam", "I kiss your neck alsoo", "25/07", R.drawable.a, Random.nextInt(2, 10)),
        ChatItem("EXCEPTION", "Hey", "17/07", R.drawable.d, Random.nextInt(2, 10)),
        ChatItem("Jsvindr Sng", "https://t.me/+i_voE00fHsMOODA9", "10/07", R.drawable.e, Random.nextInt(2, 10)),
        ChatItem("Apple", "https://t.me/+qnGC9Zd2csJkZDU9", "06/07", R.drawable.e, Random.nextInt(2, 10)),
        ChatItem("Binary Trading Trader", "Ftgmn...", "05/07", R.drawable.a, Random.nextInt(2, 10)),
        ChatItem("Tronix Bot", "🦴🦴🦴🦴🦴", "04/07", R.drawable.b, Random.nextInt(2, 10))
    )

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .wrapContentSize()
    ) {
        items(chats) { chat ->
            ChatRow(chat)
            Divider(color = Color.Gray, thickness = 0.1.dp)
        }
    }
}

@Composable
fun ChatRow(chat: ChatItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = chat.profileImage),
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
                text = chat.date,
                color = Color.Gray
            )
            if (chat.unreadCount > 0) {
                BadgeBox(chat.unreadCount)
            }
        }
    }
}

@Composable
fun BadgeBox(unreadCount: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(24.dp)
            .background(Telegram, CircleShape)
    ) {
        Text(text = unreadCount.toString(), color = Color.White)
    }
}

data class ChatItem(
    val name: String,
    val message: String,
    val date: String,
    val profileImage: Int,
    val unreadCount: Int
)

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3200px,dpi=560" // This simulates a 6.7-inch screen with 1440x3200 resolution and 560 dpi
)
@Composable
fun TelegramScreenPreview() {
    TelegramScreen()
}
