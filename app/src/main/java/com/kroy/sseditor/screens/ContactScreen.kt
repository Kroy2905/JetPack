package com.kroy.sseditor.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kroy.ssediotor.R
import com.kroy.sseditor.models.ApiResponse
import com.kroy.sseditor.models.ContactItem
import com.kroy.sseditor.ui.theme.CustomTypography
import com.kroy.sseditor.ui.theme.Primary
import com.kroy.sseditor.utils.Utils.base64ToBitmap
import com.kroy.sseditor.viewmodels.ContactViewModel

@Composable
fun ContactScreen(onAddContact: () -> Unit = {}, onEditClick: (ContactItem) -> Unit = {}) {
    // Commenting out the ViewModel for the preview
    // val contactViewModel: ContactViewModel = hiltViewModel()
    // val allContacts: State<ApiResponse.AllContactResponse?> = contactViewModel.filteredContactResponse.collectAsState()

    // Using a dummy list for preview
    val dummyContacts = listOf(
        ContactItem(contactName = "John Doe", comment1 = "Hello how are ", contactId = 1, comment2 = "22", comment3 = "4"),
        ContactItem(contactName = "Jane Smith"))


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

            ContactList(contacts = dummyContacts, onEditClick = onEditClick)
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
fun ContactList(contacts: List<ContactItem>, onEditClick: (ContactItem) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.2.dp)
    ) {
        items(contacts) { contact ->
            ContactItem(contact = contact, onEditClick)
        }
    }
}

@Composable
fun ContactItem(contact: ContactItem, onEditClick: (ContactItem) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
            .clickable {  }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = contact.contactName,
                modifier = Modifier
                    .padding(start = 5.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {

                // fetch  the data and generate the screenshot
                //TODO()  - Use @CustomeTelegramLayput to generate the screenshot

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
    ContactScreen()
}
