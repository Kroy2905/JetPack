package com.kroy.sseditor.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.kroy.ssediotor.R
import com.kroy.sseditor.models.ApiResponse
import com.kroy.sseditor.models.Client
import com.kroy.sseditor.models.addClientBody
import com.kroy.sseditor.ui.theme.CustomBoldTypography
import com.kroy.sseditor.ui.theme.CustomTypography
import com.kroy.sseditor.ui.theme.Dimens
import com.kroy.sseditor.ui.theme.Primary
import com.kroy.sseditor.viewmodels.AddClientViewModel
import com.kroy.sseditor.viewmodels.ClientViewModel
import java.io.ByteArrayOutputStream


@Preview(showBackground = true)

@Composable
fun preview3(){


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun AddClientScreen(onClientAdded: (Int) -> Unit) {
    var clientName by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBase64 by remember { mutableStateOf("") }
    val context = LocalContext.current

    val addClientViewModel:AddClientViewModel = hiltViewModel()
    val addClients: State<ApiResponse.AddClientResponse?> = addClientViewModel.filteredaddClientResponse.collectAsState()
    val userId: State<Int> = addClientViewModel.userIdFlow.collectAsState(0)
    var hasNavigated by remember { mutableStateOf(false) } // Track if navigation has occurred
   // val addedClientId = addClients.value?.data?.clientId
    //Log.d("add client ->" , "${addedClientId}")
    val addedClientId = addClients.value?.data?.clientId ?: 0 // Safely access the clientId, default to 0 if null
    Log.d("add client ->", "Added Client ID: $addedClientId")

    if (addedClientId != 0 && !hasNavigated) {
        hasNavigated = true // Set the flag to true after first navigation
        onClientAdded(userId.value) // Trigger navigation or further actions
        addClientViewModel.resetClientState() // Re
    }


    // Activity Result API for image picking
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            val inputStream = context.contentResolver.openInputStream(it)

            // Decode the input stream into a Bitmap
            val originalBitmap = BitmapFactory.decodeStream(inputStream)

            // Create a ByteArrayOutputStream to hold the compressed image
            val outputStream = ByteArrayOutputStream()

            // Compress the bitmap to reduce quality (e.g., 50%)
            originalBitmap?.compress(Bitmap.CompressFormat.JPEG, 30, outputStream) // Adjust the quality as needed
            val compressedBytes = outputStream.toByteArray()

            // Convert the compressed image to Base64
            imageBase64 = Base64.encodeToString(compressedBytes, Base64.DEFAULT)
        }
    }


    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Heading above the LazyColumn
        Text(
            text = "Add Client",
            style = CustomBoldTypography.titleMedium.copy(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Primary
            ),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Client Name Text Field
        OutlinedTextField(
            value = clientName,
            onValueChange = { clientName = it },
            label = { Text("Client Name", color = Primary, fontWeight = FontWeight.Normal) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Primary,
                cursorColor = Primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Image Selection Button
        Button(
            onClick = {
                // Launch the image picker
                imagePickerLauncher.launch("image/*")
            },
            modifier = Modifier
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Color.White
            )
        ) {
            Text("Select Image", Modifier.padding(8.dp), fontSize = Dimens.ButtonText)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show selected image or default image
        Image(
            painter = rememberAsyncImagePainter(selectedImageUri ?: R.drawable.default_pic), // Default image resource
            contentDescription = "Client Image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f)
                .weight(1f) // Occupies remaining space
                .padding(vertical = 16.dp),
            contentScale = ContentScale.Fit
        )

        // Add Client Button
        Button(
            onClick = {
                if (clientName.isNotEmpty() && imageBase64.isNotEmpty()) {
                    addClientViewModel.addClient(addClientBody(
                        userId = userId.value,
                        clientImage = imageBase64,
                        clientName = clientName
                    ))
                }else{
                    Toast.makeText(context, "Error Saving .. ", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp) // 10.dp from bottom
                .fillMaxWidth(0.9f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Color.White
            )
        ) {
            Text("Save", Modifier.padding(8.dp), fontSize = Dimens.ButtonText)
        }
    }
}
