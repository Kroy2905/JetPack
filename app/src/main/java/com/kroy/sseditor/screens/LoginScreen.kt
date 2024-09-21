package com.kroy.sseditor.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kroy.ssediotor.R
import com.kroy.sseditor.ui.theme.CustomFontFamily
import com.kroy.sseditor.ui.theme.CustomTypography
import com.kroy.sseditor.ui.theme.Primary
import com.kroy.sseditor.ui.theme.PrimaryLight

@Preview
@Composable
fun preview(){
    LoginScreen { userId, password ->
        // Handle login action
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSubmit: (String, String) -> Unit,

) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Primary
//                brush = Brush.radialGradient(
//                    colors = listOf(PrimaryLight, Primary), // Gradient colors
//                )
            )
            .padding(16.dp)
    ) {
        // Logo at the top-left corner
        Image(
            painter = painterResource(id = R.drawable.f),  // Logo image
            contentDescription = "Logo",
            modifier = Modifier
                .size(80.dp) // Adjust logo size
                .align(Alignment.TopStart),
            contentScale = ContentScale.Fit
        )

        // Box for login form
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .fillMaxHeight(.4f)
                .clip(RoundedCornerShape(16.dp)) // Rounded corners for the Box
                .background(color = Color.White) // Box background color
                .padding(16.dp)
                .align(Alignment.Center) // Center the login form
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Text above the User ID field
                Text(
                    text = "Login",
                    fontWeight = FontWeight.ExtraBold,
                    style = CustomTypography.titleLarge, // Use custom typography
                    color = Primary,
                    fontSize = 34.sp,

                )

                // User ID Text Field
                OutlinedTextField(
                    value = userId,
                    onValueChange = { userId = it },
                    label = { Text(text = "Username", color = Primary) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = Primary,
                        cursorColor = Primary
                    )
                )

                // Password Text Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Password", color = Primary) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = Primary,
                        cursorColor = Primary
                    )
                )
                Spacer(modifier = Modifier

                    .height(5.dp)
                    .fillMaxWidth(),

                    )

                // Submit Button
                Button(
                    onClick = { onSubmit(userId, password) },
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Submit", modifier = Modifier
                        .padding(8.dp))
                }
            }
        }
    }
}
