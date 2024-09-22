package com.kroy.sseditor.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kroy.ssediotor.R
import com.kroy.sseditor.models.ApiResponse
import com.kroy.sseditor.models.clientItem
import com.kroy.sseditor.ui.theme.CustomTypography
import com.kroy.sseditor.ui.theme.Primary
import com.kroy.sseditor.viewmodels.ClientViewModel

@Composable
fun ClientScreen(onAddClient: () -> Unit, onClientClick: (clientItem) -> Unit) {
    val clientViewModel: ClientViewModel = hiltViewModel()
    val allClients: State<ApiResponse.AllClientResponse?> = clientViewModel.filteredClientResponse.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Clients",
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

            ClientList(clients = allClients.value?.data ?: emptyList(), onClick = onClientClick)
        }
            // Explicitly using Modifier.align here
            FloatingActionButton(
                onClick = onAddClient,
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd) // Explicitly using Modifier
                    .padding(16.dp),
                containerColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Client",
                    tint = Primary
                )
            }
        }

}
fun base64ToBitmap(base64String: String): Bitmap? {
    return try {
        val decodedString = Base64.decode(base64String, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun ClientList(clients: List<clientItem>, onClick: (clientItem) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.2.dp)
    ) {
        items(clients) { client ->
            ClientItem(client = client, onClick)
        }
    }
}

@Composable
fun ClientItem(client: clientItem, onClick: (clientItem) -> Unit) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(client.clientImage) {
        bitmap = base64ToBitmap(client.clientImage)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
            .clickable { onClick(client) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = client.clientName,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } ?: run {
            // Placeholder for loading state or error state
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = client.clientName,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = Color.Black
        )
    }
}
