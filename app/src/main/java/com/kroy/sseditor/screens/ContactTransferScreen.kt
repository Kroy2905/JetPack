package com.kroy.sseditor.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kroy.sseditor.models.Client
import com.kroy.sseditor.models.ContactItem
import com.kroy.sseditor.ui.theme.Primary


@Preview(showBackground = true)
@Composable
fun PreviewContactManager() {
    val dummyClients = listOf(Client("Client A",""), Client("Client B",""), Client("Client C",""))
    val dummyContacts = List(100) {
        ContactItem(it+1,"Contact ${it + 1}")
        }

    ContactTransferScreen(
        allContacts = dummyContacts,
        allClients = dummyClients,
        onTransfer = { selectedContacts ->
            println("Selected contacts for transfer: $selectedContacts")
        }
    )
}

@Composable
fun ContactTransferScreen(
    allContacts: List<ContactItem>, // List of all contacts
    allClients: List<Client>,       // List of all clients
    onTransfer: (List<String>) -> Unit // Callback for transfer action
) {
    var selectedClient by remember { mutableStateOf("") }
    var selectedDay by remember { mutableStateOf("") } // Track selected day
    var currentPage by remember { mutableStateOf(1) }
    val pageSize = 50

    val selectedContacts = remember { mutableStateListOf<ContactItem>() }
    val selectAllState = remember { mutableStateMapOf<Int, Boolean>() }

    val paginatedContacts = allContacts.chunked(pageSize)
    val currentContacts = paginatedContacts.getOrNull(currentPage - 1) ?: emptyList()

    val days = List(7) { "Day ${it + 1}" } // Days from "Day 1" to "Day 7"

    fun resetPreviousPageItems(previousPage: Int) {
        val previousContacts = paginatedContacts.getOrNull(previousPage - 1) ?: emptyList()
        selectedContacts.removeAll(previousContacts) // Remove items from the previous page
        selectAllState[previousPage] = false // Reset "Select All" for the previous page
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Client Dropdown
        DropDownMenu(
            allItems = allClients.map { it.name },
            selectedItem = selectedClient,
            placeholder = "Select Client",
            onItemSelected = { selectedClient = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Day Dropdown
        DropDownMenu(
            allItems = days,
            selectedItem = selectedDay,
            placeholder = "Select Day",
            onItemSelected = { selectedDay = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Select All Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.wrapContentSize()
        ) {
            val selectAll = selectAllState[currentPage] ?: false

            Checkbox(
                checked = selectAll,
                onCheckedChange = { isChecked ->
                    selectAllState[currentPage] = isChecked
                    if (isChecked) {
                        selectedContacts.addAll(currentContacts)
                    } else {
                        selectedContacts.removeAll(currentContacts)
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Primary,
                    uncheckedColor = Primary,
                    checkmarkColor = Color.White
                )
            )
            Text(
                "Select All",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Primary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Contact List
        LazyColumn(
            modifier = Modifier
                .border(2.dp, Primary)
                .weight(1f)
        ) {
            items(currentContacts) { contact ->
                val isChecked = selectedContacts.contains(contact)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(2.dp)
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                selectedContacts.add(contact)
                            } else {
                                selectedContacts.remove(contact)
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Primary,
                            uncheckedColor = Primary,
                            checkmarkColor = Color.White
                        )
                    )
                    Text(
                        "${contact.contactId}. ${contact.contactName}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 8.dp),
                        color = Primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pagination Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(2.dp, Primary)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (currentPage > 1) {
                        resetPreviousPageItems(currentPage)
                        currentPage--
                    }
                },
                enabled = currentPage > 1,
                colors = ButtonDefaults.buttonColors(Primary)
            ) {
                Text("Previous", color = Color.White)
            }

            Text(
                "Page $currentPage / ${paginatedContacts.size}",
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                color = Primary
            )

            Button(
                onClick = {
                    if (currentPage < paginatedContacts.size) {
                        resetPreviousPageItems(currentPage)
                        currentPage++
                    }
                },
                enabled = currentPage < paginatedContacts.size,
                colors = ButtonDefaults.buttonColors(Primary)
            ) {
                Text("Next", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Transfer Button
        Button(
            onClick = { onTransfer(selectedContacts.map { it.contactName }) },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(19.dp)),
            colors = ButtonDefaults.buttonColors(Primary)
        ) {
            Text(
                "Transfer",
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
                color = Color.White
            )
        }
    }
}

@Composable
fun DropDownMenu(
    allItems: List<String>,
    selectedItem: String,
    placeholder: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Text(
            text = if (selectedItem.isNotEmpty()) selectedItem else placeholder,
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .border(2.dp, Primary)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(18.dp),
            color = Primary
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            allItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = item,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W500,
                            color = Color.White
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Primary)
                )
            }
        }
    }
}


@Composable
fun DropDownMenu(
    allClients: List<Client>,
    selectedClient: String,
    onClientSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Text(
            text = if (selectedClient.isNotEmpty()) selectedClient else "Select Client",
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .border(2.dp, Primary)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(18.dp),
            color = Primary
        )

      DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
          modifier = Modifier
              .padding(0.dp,0.dp)
              .fillMaxWidth(.92f)
        ) {
            allClients.forEach { client ->
                DropdownMenuItem(
                    onClick = {
                        onClientSelected(client.name)
                        expanded = false
                    },
                    text = {
                        Text(client.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W500,
                            color = Color.White
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Primary)
                )
            }
        }
    }
}

