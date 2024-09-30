package com.kroy.sseditor.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroy.sseditor.models.ApiResponse
import com.kroy.sseditor.models.addClientBody
import com.kroy.sseditor.repository.SSEditorRepository
import com.kroy.sseditor.utils.DataStoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddClientViewModel @Inject constructor(
    private val dataStoreHelper: DataStoreHelper,
    private val repository: SSEditorRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Expose the DataStore data as Flow
    val isLoggedInFlow: Flow<Boolean> = dataStoreHelper.isLoggedInFlow
    val userIdFlow: Flow<Int> = dataStoreHelper.userIdFlow

    // StateFlow for all clients response


    // Functions to update user state
    private fun setIsLoggedIn(isLoggedIn: Boolean) {
        viewModelScope.launch {
            dataStoreHelper.setIsLoggedIn(isLoggedIn)
        }
    }

    private fun setUserId(userId: Int) {
        viewModelScope.launch {
            dataStoreHelper.setUserId(userId)
        }
    }



    // Fetch all clients and handle the response
    private val addClients: StateFlow<ApiResponse>
        get() = repository.addClients

    // Filtered response for all clients
    private val _filteredaddClientResponse = MutableStateFlow<ApiResponse.AddClientResponse?>(null)
    val filteredaddClientResponse: StateFlow<ApiResponse.AddClientResponse?> get() = _filteredaddClientResponse
    fun addClient(addClientBody: addClientBody,context: Context) {
        viewModelScope.launch {

            repository.addClient(addClientBody,context)

            // Assuming repository.allClients is updated after the API call
            addClients.collect { response ->

                handleClientResponse(response)
            }
        }
    }




    // Handle and filter the API response
    private fun handleClientResponse(response: ApiResponse) {
        when (response) {
            is ApiResponse.AddClientResponse -> {
                if (response.data!=null) {
                    // Emit the successful response
                    _filteredaddClientResponse.value = response
                } else {
                    // Handle empty data scenario
                    _filteredaddClientResponse.value = ApiResponse.AddClientResponse(
                        data = null,
                        message = "No clients found",
                        statusCode = response.statusCode
                    )

                }
            }
            else -> {
            }
        }
    }

    fun resetClientState() {
        _filteredaddClientResponse.value = null
    }

}
