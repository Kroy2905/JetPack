package com.kroy.sseditor.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kroy.sseditor.models.Client
import com.kroy.sseditor.models.ContactItem
import com.kroy.sseditor.models.clientItem

class SharedViewModel : ViewModel() {
    private val _clients = MutableLiveData<List<Client>>()
    val clients: LiveData<List<Client>> get() = _clients

    private val _contacts = MutableLiveData<List<ContactItem>>()
    val contacts: LiveData<List<ContactItem>> get() = _contacts

    fun setClients(clientList: List<Client>) {
        _clients.value = clientList
    }

    fun setContacts(contactList: List<ContactItem>) {
        _contacts.value = contactList
    }
    fun filterAndSetClients(clientItemList: List<clientItem>) {
        val filteredClients = clientItemList
            .filter { clientItem ->
                // Apply your filtering logic here
                // For example, filter out clients with empty names
                clientItem.clientName.isNotBlank()
            }
            .map { clientItem ->
                // Transform ClientItem to Client if needed
                Client(name = clientItem.clientName, imageBase64 = clientItem.clientImage)
            }

        // Update the LiveData with the filtered and mapped list
        _clients.value = filteredClients
    }

}
