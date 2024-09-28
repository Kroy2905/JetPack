package com.kroy.sseditor.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kroy.ssediotor.R
import com.kroy.sseditor.ui.theme.Primary
import com.kroy.sseditor.utils.Utils.base64ToBitmap
import com.kroy.sseditor.utils.Utils.drawableToBase64

import java.io.ByteArrayOutputStream
import java.io.PipedReader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContactScreen(
    defaultClientImageBase64: String,
    defaultBackgroundImageBase64: String,
    comment1: String = "",
    comment2: String = "",
    comment3: String = ""
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedBackgroundUri by remember { mutableStateOf<Uri?>(null) }
    var imageBase64 by remember { mutableStateOf(defaultClientImageBase64) }
    var backgroundImageBase64 by remember { mutableStateOf(defaultBackgroundImageBase64) }
    var comment1State by remember { mutableStateOf(comment1) }
    var comment2State by remember { mutableStateOf(comment2) }
    var comment3State by remember { mutableStateOf(comment3) }
    val context = LocalContext.current

    val clientImageBitmap = remember { base64ToBitmap(defaultClientImageBase64) }
    val backgroundImageBitmap = remember { base64ToBitmap(defaultBackgroundImageBase64) }

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
            text = "Edit Contact",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Primary,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

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
            Text("Select Contact Image", Modifier.padding(8.dp), fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            bitmap = selectedImageUri?.let {
                BitmapFactory.decodeStream(context.contentResolver.openInputStream(it)).asImageBitmap()
            } ?: clientImageBitmap?.asImageBitmap()!!,
            contentDescription = "Contact Image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f)
                .weight(1f)
                .padding(vertical = 16.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            Text("Select Background Image", Modifier.padding(8.dp), fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            bitmap = selectedBackgroundUri?.let {
                BitmapFactory.decodeStream(context.contentResolver.openInputStream(it)).asImageBitmap()
            } ?: backgroundImageBitmap?.asImageBitmap()!!,
            contentDescription = "Background Image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f)
                .weight(1f)
                .padding(vertical = 16.dp),
            contentScale = ContentScale.Fit
        )

        // Comment Fields
        OutlinedTextField(
            value = comment1State,
            onValueChange = { comment1State = it },
            label = { Text("Comment 1") },
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Primary,
                cursorColor = Primary
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = comment2State,
            onValueChange = { comment2State = it },
            label = { Text("Comment 2") },
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Primary,
                cursorColor = Primary
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = comment3State,
            onValueChange = { comment3State = it },
            label = { Text("Comment 3") },
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Primary,
                cursorColor = Primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (imageBase64.isNotEmpty()) {
                    Toast.makeText(context, "Contact Updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error Updating .. ", Toast.LENGTH_SHORT).show()
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
            Text("Save", Modifier.padding(8.dp), fontSize = 16.sp)
        }
    }
}

@Preview
@Composable
fun PreviewEditContactScreen() {
    val context = LocalContext.current
    EditContactScreen(
        defaultClientImageBase64 = drawableToBase64(context, R.drawable.f),
        defaultBackgroundImageBase64 = drawableToBase64(context, R.drawable.d),
        comment1 = "Initial Comment 1",
        comment2 = "Initial Comment 2",
        comment3 = "Initial Comment 3"
    )
}
