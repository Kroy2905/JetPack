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
fun PreviewAddContactScreen() {
    AddContactScreen(onContactAdded = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreen(onContactAdded: (Int) -> Unit) {
    var contactName by remember { mutableStateOf("") }
    var comment1 by remember { mutableStateOf("") }
    var comment2 by remember { mutableStateOf("") }
    var comment3 by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedBackgroundUri by remember { mutableStateOf<Uri?>(null) }
    var imageBase64 by remember { mutableStateOf("") }
    var backgroundImageBase64 by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Comment out the ViewModel code for now
    // val addContactViewModel: AddContactViewModel = hiltViewModel()
    // val addContacts: State<ApiResponse.AddContactResponse?> = addContactViewModel.filteredAddContactResponse.collectAsState()
    // val userId: State<Int> = addContactViewModel.userIdFlow.collectAsState(0)
    // var hasNavigated by remember { mutableStateOf(false) }
    // val addedContactId = addContacts.value?.data?.contactId ?: 0

    // Log.d("add contact ->", "Added Contact ID: $addedContactId")

    // if (addedContactId != 0 && !hasNavigated) {
    //     hasNavigated = true
    //     onContactAdded(userId.value)
    //     addContactViewModel.resetContactState()
    // }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            val inputStream = context.contentResolver.openInputStream(it)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            val outputStream = ByteArrayOutputStream()
            originalBitmap?.compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
            val compressedBytes = outputStream.toByteArray()
            imageBase64 = Base64.encodeToString(compressedBytes, Base64.DEFAULT)
        }
    }

    val backgroundPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedBackgroundUri = it
            val inputStream = context.contentResolver.openInputStream(it)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            val outputStream = ByteArrayOutputStream()
            originalBitmap?.compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
            val compressedBytes = outputStream.toByteArray()
            backgroundImageBase64 = Base64.encodeToString(compressedBytes, Base64.DEFAULT)
        }
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Add Contact",
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

        OutlinedTextField(
            value = contactName,
            onValueChange = { contactName = it },
            label = { Text("Contact Name", color = Primary) },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Primary,
                cursorColor = Primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Additional Text Fields for comments
        OutlinedTextField(
            value = comment1,
            onValueChange = { comment1 = it },
            label = { Text("Comment 1", color = Primary) },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Primary,
                cursorColor = Primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = comment2,
            onValueChange = { comment2 = it },
            label = { Text("Comment 2", color = Primary) },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Primary,
                cursorColor = Primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = comment3,
            onValueChange = { comment3 = it },
            label = { Text("Comment 3", color = Primary) },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Primary,
                cursorColor = Primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contact Image Selection Button
        Button(
            onClick = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Color.White
            )
        ) {
            Text("Select Contact Image", Modifier.padding(8.dp), fontSize = Dimens.ButtonText)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show selected contact image or default image
        Image(
            painter = rememberAsyncImagePainter(selectedImageUri ?: R.drawable.default_pic),
            contentDescription = "Contact Image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f)
                .weight(1f)
                .padding(vertical = 16.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Background Image Selection Button
        Button(
            onClick = { backgroundPickerLauncher.launch("image/*") },
            modifier = Modifier
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Color.White
            )
        ) {
            Text("Select Background Image", Modifier.padding(8.dp), fontSize = Dimens.ButtonText)
        }

        // Show selected background image or default image
        Image(
            painter = rememberAsyncImagePainter(selectedBackgroundUri ?: R.drawable.default_pic),
            contentDescription = "Background Image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f)
                .weight(1f)
                .padding(vertical = 16.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save Button
        Button(
            onClick = {
                if (contactName.isNotEmpty() && imageBase64.isNotEmpty()) {
                    // Save logic here (commented out ViewModel code)
                    // addContactViewModel.addContact(
                    //     addContactBody(
                    //         userId = userId.value,
                    //         contactImage = imageBase64,
                    //         contactName = contactName,
                    //         comment1 = comment1,
                    //         comment2 = comment2,
                    //         comment3 = comment3
                    //     )
                    // )
                } else {
                    Toast.makeText(context, "Error Saving .. ", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp)
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
