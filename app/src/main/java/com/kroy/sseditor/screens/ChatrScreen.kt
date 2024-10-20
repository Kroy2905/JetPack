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
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kroy.ssediotor.R
import com.kroy.sseditor.ui.theme.CustomBoldTypography
import com.kroy.sseditor.ui.theme.CustomRobotoMediumFontFamily
import com.kroy.sseditor.ui.theme.Dimens
import com.kroy.sseditor.ui.theme.Telegram
import com.kroy.sseditor.ui.theme.TelegramDark
import kotlin.random.Random

@Composable
fun ChatScreen() {
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
                  .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(

                            Color.LightGray.copy(alpha = 0.1f),
                            Color.LightGray.copy(alpha = 0.1f),

                            )
                    )
                    )
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        )
    }
}
@Composable
fun StatusBar() {
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
                text = "11:05",
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
                        .padding(top = 4.dp,end= 19.dp)
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
    Row(
        modifier = modifier
            .fillMaxWidth(),

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
            .padding(top = 4.dp,end=18.dp)
            .size(size.dp)
            .background(Telegram, CircleShape)
    ) {
        Text(text = unreadCount.toString(), color = Color.White , fontSize = 12.sp)
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
    ChatScreen()
}
