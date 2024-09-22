package com.kroy.sseditor.screens

import android.app.Activity
import androidx.compose.material3.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.kroy.sseditor.ui.theme.CustomBoldTypography
import com.kroy.sseditor.ui.theme.CustomTypography
import com.kroy.sseditor.ui.theme.Primary
import java.util.Calendar


@Preview
@Composable
fun preview4(){

    SevenDayScreen()
}


@Composable
fun SevenDayScreen() {
    val timeStates = remember { mutableStateListOf("08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "01:00 PM", "02:00 PM") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary)
            .padding(16.dp),
    ) {
        Text(
            text = "Select Times for Each Day",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Text(
            text = "Client : Koustav ", // client name goes here 
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 16.dp, start = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color.White
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(7) { dayIndex ->
                DayWithTimePicker(
                    day = (dayIndex + 1).toString(),
                    time = timeStates[dayIndex],
                    onTimeSelected = { newTime ->
                        timeStates[dayIndex] = newTime
                    }
                )
            }
        }
    }
}


@Composable
fun DayWithTimePicker(day: String, time: String, onTimeSelected: (String) -> Unit) {
    var showTimePicker by remember { mutableStateOf(false) }
    var hour by remember { mutableStateOf(8) }
    var minute by remember { mutableStateOf(0) }

    // Show time picker dialog if needed
    if (showTimePicker) {
        TimePickerDialog(
            initialHour = hour,
            initialMinute = minute,
            onTimeSelected = { selectedHour, selectedMinute ->
                hour = selectedHour
                minute = selectedMinute
                onTimeSelected(formatTime(hour, minute))
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "DAY $day :", modifier = Modifier.weight(.5f),
            style = CustomBoldTypography.titleMedium, fontSize = 16.sp
            )

        Text(text = " $time", modifier = Modifier.weight(.8f),
            style = CustomTypography.titleMedium, fontSize = 16.sp
        )

        // Button to open the time picker
        Button(onClick = { showTimePicker = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Color.White
            )) {
            Text("Set Time")
        }

        // Go button
        IconButton(onClick = {
            println("Selected: Day $day at ${formatTime(hour, minute)}")
        }) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Go")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var hour by remember { mutableStateOf(initialHour) }
    var minute by remember { mutableStateOf(initialMinute) }
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )



    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = { Text("Select Time") },
        text = {
            Column (){
                // Your TimePicker UI goes here
                TimePicker(
                  timePickerState,
                    colors = TimePickerDefaults.colors(

                        selectorColor = Primary,
                        containerColor = Primary

                        // Customize colors as needed
                    )
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onTimeSelected(timePickerState.hour, timePickerState.minute)
                onDismiss()


            }, colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Color.White
            )) {
                Text("OK", color = Color.White)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = Color.White
                )) {
                Text("Cancel", color = Color.White)
            }
        }
    )
}

fun formatTime(hour: Int, minute: Int): String {
    val amPm = if (hour < 12) "AM" else "PM"
    val formattedHour = if (hour % 12 == 0) 12 else hour % 12
    return String.format("%02d:%02d %s", formattedHour, minute, amPm)
}

//@Composable
//fun DayWithTimePicker(day: String, time: String, onTimeSelected: (String) -> Unit) {
//    var showDialog by remember { mutableStateOf(false) }
//    var hour by remember { mutableStateOf(8) }
//    var minute by remember { mutableStateOf(0) }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable { showDialog = true },
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(text = "Day $day: $time", modifier = Modifier.weight(1f))
//
//        IconButton(onClick = {
//            println("Selected: Day $day at $time")
//        }) {
//            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Go")
//        }
//    }
//
//    if (showDialog) {
//        TimePickerDialog(
//            hour = hour,
//            minute = minute,
//            onHourChange = { hour = it },
//            onMinuteChange = { minute = it },
//            onDismiss = { showDialog = false },
//            onConfirm = {
//                val formattedTime = String.format("%02d:%02d %s", if (hour > 12) hour - 12 else hour, minute, if (hour >= 12) "PM" else "AM")
//                onTimeSelected(formattedTime)
//                showDialog = false
//            }
//        )
//    }
//}
//
//@Composable
//fun TimePickerDialog(
//    hour: Int,
//    minute: Int,
//    onHourChange: (Int) -> Unit,
//    onMinuteChange: (Int) -> Unit,
//    onDismiss: () -> Unit,
//    onConfirm: () -> Unit
//) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = { Text("Select Time") },
//        text = {
//            Column {
//                Text("Hour")
//                Slider(value = hour.toFloat(), onValueChange = { onHourChange(it.toInt()) }, valueRange = 0f..23f)
//
//                Text("Minute")
//                Slider(value = minute.toFloat(), onValueChange = { onMinuteChange(it.toInt()) }, valueRange = 0f..59f)
//            }
//        },
//        confirmButton = {
//            TextButton(onClick = onConfirm) {
//                Text("OK")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel")
//            }
//        }
//    )
//}
