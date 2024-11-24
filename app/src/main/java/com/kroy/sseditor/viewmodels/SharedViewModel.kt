package com.kroy.sseditor.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kroy.sseditor.models.Client
import com.kroy.sseditor.models.ContactItem
import com.kroy.sseditor.models.clientItem

class SharedViewModel : ViewModel() {
    private val _clients = MutableLiveData<List<clientItem>>()
    val clients: LiveData<List<clientItem>> get() = _clients

    private val _contacts = MutableLiveData<List<ContactItem>>()
    val contacts: LiveData<List<ContactItem>> get() = _contacts


    fun setContacts(contactList: List<ContactItem>) {
        _contacts.value = contactList
    }
    fun setClients(clientItemList: List<clientItem>) {
        _clients.value = clientItemList
    }

}
