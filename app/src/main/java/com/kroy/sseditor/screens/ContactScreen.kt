package com.kroy.sseditor.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kroy.ssediotor.R
import com.kroy.sseditor.models.ApiResponse
import com.kroy.sseditor.models.ChatMessage
import com.kroy.sseditor.models.ContactItem
import com.kroy.sseditor.ui.theme.CustomTypography
import com.kroy.sseditor.ui.theme.Primary
import com.kroy.sseditor.utils.SelectedClient
import com.kroy.sseditor.utils.Utils.CaptureAndSaveComposable
import com.kroy.sseditor.utils.Utils.base64ToBitmap
import com.kroy.sseditor.viewmodels.ContactViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.random.Random


@RequiresApi(Build.VERSION_CODES.O)
val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

@RequiresApi(Build.VERSION_CODES.O)
fun addRandomMinutesToTime(initialTime: String, min: Int, max: Int): String {
    val trimmedTime = initialTime.trim()

    return try {
        val parsedTime = LocalTime.parse(trimmedTime, timeFormatter)

        // Generate a random number of minutes within the range
        val randomMinutes = Random.nextInt(min, max)

        // Adjust the parsed time by the random minutes
        val adjustedTime = parsedTime.plusMinutes(randomMinutes.toLong())

        // Format and return the adjusted time in 12-hour format
        adjustedTime.format(timeFormatter)
    } catch (e: DateTimeParseException) {
        // In case of error, return the original time
        initialTime
    }
}


@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContactScreen(onAddContact: () -> Unit = {}, onEditClick: (ContactItem) -> Unit = {}) {
    val contactViewModel: ContactViewModel = hiltViewModel()
    val isLoading: State<Boolean> = contactViewModel.isLoading.collectAsState()

    val allContacts: State<ApiResponse.AllContactResponse?> = contactViewModel.filteredContactResponse.collectAsState()
    val contactDetails: State<ApiResponse.ContactDetailsResponse?> = contactViewModel.filteredgetContactDetailsResponse.collectAsState()

    Log.d("contact loading ->", " loading value ${isLoading.value} ,${contactDetails.value?.data?.contactId} ")

    if (contactDetails.value?.data != null && isLoading.value) {
        Log.d("contact loading ->", " Entered image saving  ")

        val comments = mutableStateListOf<String>().apply {
            add(contactDetails.value?.data!!.comment1)
            add(contactDetails.value?.data!!.comment2)
            add(contactDetails.value?.data!!.comment3)
        }

        // Prepare other parameters for CaptureAndSaveComposable
        val contactName = contactDetails.value?.data!!.contactName
        val contactPic = base64ToBitmap(contactDetails.value?.data!!.contactImage)

        // Start with the initial time
        var currentTime = addRandomMinutesToTime(SelectedClient.time, 1, 2)

        // Generate times for each message with specific differences
        val messages = comments
            .filter { it.trim().isNotEmpty() }
            .mapIndexed { index, trimmedComment ->
                // Update the time for each message based on the index
                currentTime = when (index) {
                    0 -> addRandomMinutesToTime(currentTime, 1, 2) // 1st message: 1-2 min difference
                    1 -> addRandomMinutesToTime(currentTime, 2, 3) // 2nd message: 2-3 min difference
                    2 -> addRandomMinutesToTime(currentTime, 5, 7) // 3rd message: 5-7 min difference
                    else -> currentTime
                }

                ChatMessage(trimmedComment.trim(), currentTime, isSender = true)
            }

        // Update SelectedClient.time for future use
      //  SelectedClient.time = addRandomMinutesToTime(SelectedClient.time, 2, 30)

        val initialTimeString = currentTime // New time after random increment
        val backgroundBitmap = base64ToBitmap(SelectedClient.backgroundImage)
        val senderImage = base64ToBitmap(contactDetails.value?.data!!.uploadedImage)
        val userReplySticker = base64ToBitmap(SelectedClient.clientImage)

        Log.d("time set->", "contact $initialTimeString")

        // Call the saving function
        CaptureAndSaveComposable(
            contactName = contactName,
            contactPic = contactPic,
            messages = messages,
            initialTimeString = currentTime,
            backgroundBitmap = backgroundBitmap,
            senderImage = senderImage,
            userReplySticker = userReplySticker,
            contactViewModel
        )
    }

    // Using a dummy list for preview
    val dummyContacts = listOf(
        ContactItem(contactName = "John Doe"),
        ContactItem(contactName = "Jane Smith")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Contacts",
                style = CustomTypography.titleLarge.copy(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            ContactList(contacts = allContacts.value?.data ?: emptyList(), onEditClick = onEditClick, contactViewModel = contactViewModel)
        }

        // Show CircularProgressIndicator if loading is true
        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Primary
            )
        }

        FloatingActionButton(
            onClick = onAddContact,
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add Contact",
                tint = Primary
            )
        }
    }
}

@Composable
fun ContactList(contacts: List<ContactItem>, onEditClick: (ContactItem) -> Unit,contactViewModel: ContactViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.2.dp)
    ) {
        items(contacts) { contact ->
            ContactItem(contact = contact, onEditClick,contactViewModel)
        }
    }
}

@Composable
fun ContactItem(contact: ContactItem, onEditClick: (ContactItem) -> Unit,contactViewModel: ContactViewModel) {
val context = LocalContext.current
    val isLoading: State<Boolean> = contactViewModel.isLoading.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
            .clickable { }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = contact.contactName,
                modifier = Modifier
                    .padding(start = 6.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
               contactViewModel.setLoading(true)
                // fetch  the data and generate the screenshot
                //TODO()  - Use @CustomeTelegramLayput to generate the screenshot
                      contactViewModel.getcontactDetails(
                          contactId = contact.contactId,
                          context
                      )



            },
            modifier = Modifier
                .size(40.dp)
                .background(Primary, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        IconButton(
            onClick = {
                // returning the same contact on edit
                onEditClick(contact)
            },
            modifier = Modifier
                .size(40.dp)
                .background(Primary, CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Edit Image",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ContactScreenPreview() {
   // ContactScreen()
}
